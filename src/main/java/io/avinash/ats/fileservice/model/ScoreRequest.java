package io.avinash.ats.fileservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ScoreRequest {
    private ResumeData resume;
    private String jobDescription;
}