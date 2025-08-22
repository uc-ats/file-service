package io.avinash.ats.fileservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResumeData {
    private String fileName;
    private String text;
    private Sections sections;
    private MetadataInfo metadata;

    @Data
    @NoArgsConstructor
    public static class Sections {
        private String education;
        private List<String> experience;
        private List<String> skills;
    }

    @Data
    @NoArgsConstructor
    public static class MetadataInfo {
        private String format;
        private double sizeKb;
    }
}

