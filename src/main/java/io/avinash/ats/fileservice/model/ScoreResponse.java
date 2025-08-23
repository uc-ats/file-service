package io.avinash.ats.fileservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ScoreResponse {
    private double score;
    private List<String> matchedSkills;
    private List<String> missingKeywords;
    private List<String> suggestedTerms;
    private Map<String, Object> details; // For weights, components, etc.
}