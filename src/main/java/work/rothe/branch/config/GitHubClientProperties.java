package work.rothe.branch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Duration;

@ConfigurationProperties(prefix = "work.rothe.branch.github")
public record GitHubClientProperties(String usersUrl, String reposUrl,
                                     @DefaultValue("10s") Duration connectTimeout,
                                     @DefaultValue("10s") Duration readTimeout
) {

}
