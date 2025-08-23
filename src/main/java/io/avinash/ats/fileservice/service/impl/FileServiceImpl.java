package io.avinash.ats.fileservice.service.impl;

import io.avinash.ats.fileservice.client.AtsNlpClient;
import io.avinash.ats.fileservice.exception.IdNotFoundException;
import io.avinash.ats.fileservice.model.NLPRequest;
import io.avinash.ats.fileservice.model.ResumeData;
import io.avinash.ats.fileservice.model.ScoreRequest;
import io.avinash.ats.fileservice.model.ScoreResponse;
import io.avinash.ats.fileservice.model.entity.AtsFile;
import io.avinash.ats.fileservice.repository.FileRepository;
import io.avinash.ats.fileservice.service.FileParsingService;
import io.avinash.ats.fileservice.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileParsingService fileParsingService;
    private final AtsNlpClient atsNlpClient;

    public FileServiceImpl(FileRepository fileRepository, FileParsingService fileParsingService, AtsNlpClient atsNlpClient) {
        this.fileRepository = fileRepository;
        this.fileParsingService = fileParsingService;
        this.atsNlpClient = atsNlpClient;
    }

    @Override
    public String uploadFile(MultipartFile file, String filename, String username) throws IOException {
        AtsFile doc = new AtsFile();
        doc.setFileName(filename);
        doc.setContent(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        doc.setUsername(username);
        doc.setCreatedDateTime(LocalDateTime.now());
        doc.setJsonData(fileParsingService.parse(filename, file.getBytes()));
        fileRepository.save(doc);
        return doc.getId();
    }

    @Override
    public AtsFile getFile(String id) {
        return fileRepository.findById(id).orElse(null);
    }

    @Override
    public ResumeData getExtractedData(String id){
        AtsFile atsFile = fileRepository.findById(id).orElseThrow(()->new IdNotFoundException());
        ResumeData request = fileParsingService.toResumeData(atsFile.getJsonData());
        log.info("request : {}",atsFile.getJsonData());
        ResumeData response = atsNlpClient.extract(request);
        String responseJson = fileParsingService.toJson(response);
        log.info("response : {}",responseJson);
        atsFile.setExtractedData(responseJson);
        fileRepository.save(atsFile);
        return response;
    }

    @Override
    public ScoreResponse getScore(String id){
        AtsFile atsFile = fileRepository.findById(id).orElseThrow(()->new IdNotFoundException());
        ResumeData extractedData = fileParsingService.toResumeData(atsFile.getExtractedData());
        ScoreRequest request = new ScoreRequest();
        request.setJobDescription("");
        request.setResume(extractedData);
        log.info("request : {}",request);
        ScoreResponse scoreResponse = atsNlpClient.getScore(request);
        String data = fileParsingService.toJson(scoreResponse);
        log.info("response : {}",data);
        atsFile.setExtractedData(data);
        fileRepository.save(atsFile);
        return scoreResponse;
    }

    @Override
    public String getAnalyzedData(String id){
        AtsFile atsFile = fileRepository.findById(id).orElse(null);
        ResumeData request = fileParsingService.toResumeData(atsFile.getJsonData());
        log.info("request : {}", atsFile.getJsonData());
        String data = fileParsingService.toJson(atsNlpClient.analyze(request));
        log.info("response : {}",data);
        atsFile.setAnalyzedData(data);
        fileRepository.save(atsFile);
        return data;
    }

    @Override
    public String retrain(){
        atsNlpClient.retrain(null);
        return null;
    }
}
