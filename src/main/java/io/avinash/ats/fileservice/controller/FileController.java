package io.avinash.ats.fileservice.controller;

import io.avinash.ats.fileservice.model.ResumeData;
import io.avinash.ats.fileservice.model.entity.AtsFile;
import io.avinash.ats.fileservice.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("filename") String filename,
                         @RequestParam("username") String username) throws IOException {
        return fileService.uploadFile(file, filename, username);
    }

    @GetMapping("/{id}")
    public AtsFile getFile(@PathVariable String id) {
        return fileService.getFile(id);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable String id) {
        var file = fileService.getFile(id);
        if(file==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file.getContent().getData());
    }

    @GetMapping("/extract/{id}")
    public String extract(@PathVariable String id) {
        return fileService.getExtractedData(id);
    }

    @GetMapping("/score/{id}")
    public String getScore(@PathVariable String id) {
        return fileService.getScore(id);
    }

    @GetMapping("/analyze/{id}")
    public String getAnalyzedData(@PathVariable String id) {
        return fileService.getAnalyzedData(id);
    }

    @GetMapping("/retrain")
    public String retrain() {
        return fileService.retrain();
    }
}
