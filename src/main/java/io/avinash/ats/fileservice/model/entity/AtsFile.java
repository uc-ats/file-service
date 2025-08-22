package io.avinash.ats.fileservice.model.entity;

import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "ats_files")
public class AtsFile {
    @Id
    private String id;
    private String username;
    private String fileName;
    private Binary content;
    private LocalDateTime createdDateTime;
    private String jsonData;
    private String extractedData;
    private String scoreData;
    private String analyzedData;
}
