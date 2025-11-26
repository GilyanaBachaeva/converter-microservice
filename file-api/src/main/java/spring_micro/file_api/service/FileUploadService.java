package spring_micro.file_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring_micro.file_api.event.FileSubmittedEvent;
import spring_micro.file_api.model.ConversionJob;
import spring_micro.file_api.model.ConversionStatus;
import spring_micro.file_api.repository.ConversionJobRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadService {

    private final MinioService minioService; // ← твой существующий MinioService (перенеси из старого кода)
    private final ConversionJobRepository jobRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String BUCKET_NAME = "conversions"; // или из @Value("${minio.bucket}")

    public UUID saveFileAndPublish(MultipartFile file) {
        // 1. Валидация (по желанию: проверь тип, размер и т.д.)
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // 2. Генерация jobId и путей
        UUID jobId = UUID.randomUUID();
        String originalFilename = file.getOriginalFilename();
        String sanitizedFilename = originalFilename != null
                ? originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_")
                : "file_" + jobId.toString().substring(0, 8);
        String minioInputPath = "input/" + jobId + "/" + sanitizedFilename;

        try {
            // 3. Сохранить в MinIO
            minioService.save(file.getBytes(), minioInputPath, BUCKET_NAME);

            // 4. Сохранить в БД
            ConversionJob job = ConversionJob.builder()
                    .id(jobId)
                    .originalFileName(originalFilename)
                    .minioInputPath(minioInputPath)
                    .status(ConversionStatus.PROCESSING)
                    .build();
            jobRepository.save(job);

            // 5. Отправить в Kafka
            FileSubmittedEvent event = new FileSubmittedEvent(jobId.toString(), minioInputPath);
            kafkaTemplate.send("file.submitted", jobId.toString(), event);

            log.info("File uploaded and event published for jobId: {}", jobId);
            return jobId;

        } catch (Exception e) {
            log.error("Failed to upload file for jobId: {}", jobId, e);
            throw new RuntimeException("File upload failed", e);
        }
    }
}
