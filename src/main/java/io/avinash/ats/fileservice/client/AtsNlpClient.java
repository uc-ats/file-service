package io.avinash.ats.fileservice.client;

import io.avinash.ats.fileservice.model.NLPRequest;
import io.avinash.ats.fileservice.model.ResumeData;
import io.avinash.ats.fileservice.model.ScoreRequest;
import io.avinash.ats.fileservice.model.ScoreResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "atsNlpClient",
        url = "${python.api.url}"
)
public interface AtsNlpClient {
    @PostMapping("/nlp/extract")
    ResumeData extract(@RequestBody ResumeData request);

    @PostMapping("/nlp/score")
    ScoreResponse getScore(@RequestBody ScoreRequest request);

    @PostMapping("/nlp/analyze")
    String analyze(@RequestBody ResumeData request);

    @PostMapping("/nlp/retrain")
    String retrain(@RequestBody NLPRequest request);
}
