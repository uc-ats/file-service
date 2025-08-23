package io.avinash.ats.fileservice.service;

import io.avinash.ats.fileservice.model.ResumeData;
import io.avinash.ats.fileservice.model.ScoreResponse;
import io.avinash.ats.fileservice.model.entity.AtsFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(MultipartFile file, String filename, String username) throws IOException;

    AtsFile getFile(String id);

    ResumeData getExtractedData(String id);

    ScoreResponse getScore(String id);

    String getAnalyzedData(String id);

    String retrain();
}
