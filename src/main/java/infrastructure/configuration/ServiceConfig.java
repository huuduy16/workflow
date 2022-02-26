package infrastructure.configuration;

import infrastructure.properties.CorsProperties;
import infrastructure.properties.JwtProperties;
import infrastructure.properties.SettingsProperties;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableConfigurationProperties({
    SettingsProperties.class,
    CorsProperties.class,
    JwtProperties.class})
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@EnableAsync
public class ServiceConfig {

    @Autowired
    private SettingsProperties settingsProperties;

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(settingsProperties.getTimeZone()));
    }
}
