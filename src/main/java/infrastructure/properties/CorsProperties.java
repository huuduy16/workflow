package infrastructure.properties;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    private List<String> allowOrigin = new ArrayList<>();
    private Long maxAge;
}
