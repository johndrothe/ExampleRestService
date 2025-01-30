package work.rothe.branch.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import work.rothe.branch.model.GitHubRepo;
import work.rothe.branch.model.GitHubUser;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpMethod.GET;
import static work.rothe.branch.model.GitHubRepo.TYPE_REPO_LIST;

@Slf4j
@Component
public class GitHubClient {
    @Value("${work.rothe.branch.github.users_url}")
    private String usersUrl;

    @Value("${work.rothe.branch.github.repos_url}")
    private String reposUrl;

    private final RestTemplate restTemplate;

    public GitHubClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<GitHubUser> getUser(String userId) {
        log.debug("Requesting user information for userId: {}", userId);
        return restTemplate.exchange(usersUrl, GET,
                new HttpEntity<>(getHeaders()),
                GitHubUser.class,
                Map.of("userId", userId));
    }

    public ResponseEntity<List<GitHubRepo>> getRepositories(String userId) {
        log.debug("Requesting repository information for userId: {}", userId);
        return restTemplate.exchange(reposUrl, GET,
                new HttpEntity<>(getHeaders()),
                TYPE_REPO_LIST,
                Map.of("userId", userId));
    }

    private HttpHeaders getHeaders() {
        return new HttpHeaders();
    }
}
