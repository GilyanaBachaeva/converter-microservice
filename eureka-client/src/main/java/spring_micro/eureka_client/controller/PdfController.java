package spring_micro.eureka_client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import spring_micro.eureka_client.service.PdfService;

@RequiredArgsConstructor
@RestController
public class PdfController {
    private final PdfService pdfService;

    @PostMapping("/convert")
    public String convertToPdf(@RequestParam("file") MultipartFile file) {
        return pdfService.createPdf(file);
    }
}
