package io.avinash.ats.fileservice.client;

import io.avinash.ats.fileservice.model.NLPRequest;
import io.avinash.ats.fileservice.model.ResumeData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "atsNlpClient",
        url = "${python.api.url}"
)
public interface AtsNlpClient {
    @PostMapping("/nlp/extract")
    String extract(@RequestBody NLPRequest request);

    @PostMapping("/nlp/score")
    String getScore(@RequestBody Object request);

    @PostMapping("/nlp/analyze")
    String analyze(@RequestBody NLPRequest request);

    @PostMapping("/nlp/retrain")
    String retrain(@RequestBody NLPRequest request);
}
