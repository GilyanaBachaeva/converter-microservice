package spring_micro.file_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_micro.file_api.model.ConversionJob;
import java.util.UUID;

@Repository
public interface ConversionJobRepository extends JpaRepository<ConversionJob, UUID> {
}
