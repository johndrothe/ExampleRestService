package work.rothe.branch.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import work.rothe.branch.exception.EmptyGitHubResponseException;
import work.rothe.branch.client.GitHubClient;
import work.rothe.branch.converter.GitHubUserConverter;
import work.rothe.branch.dto.UserDto;
import work.rothe.branch.model.GitHubRepo;
import work.rothe.branch.model.GitHubUser;

import java.util.List;

@Service
@Slf4j
public class GitHubUsersService {
    private static final String MSG_EMPTY_DETAILS = "Invalid (null) user details response for '%s'";
    private static final String MSG_EMPTY_REPOS = "Invalid (null) repositories response for '%s'";
    private final GitHubClient client;
    private final GitHubUserConverter converter;

    public GitHubUsersService(GitHubClient client, GitHubUserConverter converter) {
        this.client = client;
        this.converter = converter;
    }

    @Cacheable("githubUser")
    public UserDto getUserByIdFailFast(String userId) {
        return converter.convertUser(requestUserInfo(userId), requestRepositories(userId));
    }

    private GitHubUser requestUserInfo(String userId) throws RestClientException {
        val response = client.getUser(userId);
        if (response.hasBody()) {
            return response.getBody();
        }
        throw new EmptyGitHubResponseException(
                response.getStatusCode(),
                MSG_EMPTY_DETAILS.formatted(userId));
    }

    private List<GitHubRepo> requestRepositories(String userId) throws RestClientException {
        val response = client.getRepositories(userId);
        if (response.hasBody()) {
            return response.getBody();
        }
        throw new EmptyGitHubResponseException(
                response.getStatusCode(),
                MSG_EMPTY_REPOS.formatted(userId));
    }
}
