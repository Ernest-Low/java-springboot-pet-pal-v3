package sg.com.petpal.petpal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import sg.com.petpal.petpal.service.SecretsManagerService;

@Configuration
@Profile("prod")
public class SecretsConfig {

    @Autowired
    private SecretsManagerService secretsManagerService;

    @PostConstruct
    public void loadSecrets() throws Exception {
        String secretString = secretsManagerService.getSecret("petpal/prod");
        @SuppressWarnings("unchecked")
        Map<String, String> secrets = new ObjectMapper().readValue(secretString, Map.class);

        System.setProperty("DB_URL", secrets.get("DB_URL"));
        System.setProperty("DB_USERNAME", secrets.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", secrets.get("DB_PASSWORD"));
        System.setProperty("JWT_SECRET", secrets.get("JWT_SECRET"));
    }
}
