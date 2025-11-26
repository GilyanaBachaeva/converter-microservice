package spring_micro.file_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import spring_micro.file_api.service.FileUploadService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        UUID jobId = fileUploadService.saveFileAndPublish(file);
        return ResponseEntity.ok(new UploadResponse(jobId));
    }

    public record UploadResponse(UUID jobId) {}
}
