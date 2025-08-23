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
    private Entities entities = new Entities();
    private List<String> normalizedSkills;
    private String language = "en";

    @Data
    @NoArgsConstructor
    public static class Sections {
        private String education;
        private String experience;
        private String skills;
        private String summary;
        private String certifications;
        private String projects;
    }

    @Data
    @NoArgsConstructor
    public static class MetadataInfo {
        private String format;
        private double sizeKb;
    }

    @Data
    @NoArgsConstructor
    public static class Entities {
        private List<String> names;
        private List<String> emails;
        private List<String> phones;
        private List<String> organizations;
        private List<String> dates;
        private List<String> locations;
        private List<String> certifications;
        private List<String> titles;
        private List<String> skillPhrases;
    }
}

