package spring_micro.file_api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "conversion_jobs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionJob {

    @Id
    private UUID id;

    private String originalFileName;

    @Column(nullable = false)
    private String minioInputPath;

    private String minioOutputPath;

    @Enumerated(EnumType.STRING) // ← Вот оно! Чтобы статусы хранились как строки
    @Column(nullable = false)
    private ConversionStatus status;

    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
