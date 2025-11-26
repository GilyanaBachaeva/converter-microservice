package spring_micro.file_api.event;

import lombok.Value;
import java.io.Serializable;

@Value
public class FileSubmittedEvent implements Serializable {
    String jobId;
    String minioInputPath;
}
