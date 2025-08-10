package io.avinash.ats.fileservice.service.impl;

import io.avinash.ats.fileservice.model.entity.AtsFile;
import io.avinash.ats.fileservice.repository.FileRepository;
import io.avinash.ats.fileservice.service.FileService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public String uploadFile(MultipartFile file, String filename, String username) throws IOException {
        AtsFile doc = new AtsFile();
        doc.setFileName(filename);
        doc.setContent(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        doc.setUsername(username);
        doc.setCreatedDateTime(LocalDateTime.now());
        fileRepository.save(doc);
        return doc.getId();
    }

    @Override
    public AtsFile getFile(String id) {
        return fileRepository.findById(id).orElse(null);
    }
}
