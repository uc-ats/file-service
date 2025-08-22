package io.avinash.ats.fileservice.service.impl;

import io.avinash.ats.fileservice.client.AtsNlpClient;
import io.avinash.ats.fileservice.model.NLPRequest;
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
    public String getExtractedData(String id){
        AtsFile atsFile = fileRepository.findById(id).orElse(null);
        NLPRequest request = new NLPRequest();
        request.setFileName(atsFile.getFileName());
        request.setText(atsFile.getJsonData());
        log.info("request : {}",fileParsingService.toJson(request));
        String data = fileParsingService.toJson(atsNlpClient.extract(request));
        atsFile.setExtractedData(data);
        log.info("response : {}",data);
        return data;
    }

    @Override
    public String getScore(String id){
        AtsFile atsFile = fileRepository.findById(id).orElse(null);
        String request = atsFile.getExtractedData();
        log.info("request : {}",request);
        String data = fileParsingService.toJson(atsNlpClient.getScore(request));
        atsFile.setExtractedData(data);
        log.info("response : {}",data);
        return data;
    }

    @Override
    public String getAnalyzedData(String id){
        AtsFile atsFile = fileRepository.findById(id).orElse(null);
        NLPRequest request = new NLPRequest();
        request.setFileName(atsFile.getFileName());
        request.setText(atsFile.getJsonData());
        log.info("request : {}",fileParsingService.toJson(request));
        String data = fileParsingService.toJson(atsNlpClient.analyze(request));
        atsFile.setExtractedData(data);
        log.info("response : {}",data);
        return data;
    }

    @Override
    public String retrain(){
        atsNlpClient.retrain(null);
        return null;
    }
}
