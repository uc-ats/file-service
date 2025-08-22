package io.avinash.ats.fileservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NLPRequest {
    @JsonProperty("file_name")
    private String fileName;
    private String text;
}
