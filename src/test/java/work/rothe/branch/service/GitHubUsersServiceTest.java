package work.rothe.branch.service;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import work.rothe.branch.client.GitHubClient;
import work.rothe.branch.converter.GitHubUserConverter;
import work.rothe.branch.exception.EmptyGitHubResponseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static work.rothe.branch.samples.Samples.*;

public class GitHubUsersServiceTest {
    private final GitHubClient client = mock(GitHubClient.class);
    private final GitHubUserConverter converter = mock(GitHubUserConverter.class);
    private final GitHubUsersService service = new GitHubUsersService(client, converter);

    @Test
    void getUserByIdFailFast_Success() {
        when(client.getUser("octocat")).thenReturn(ResponseEntity.ok(sampleUser()));
        when(client.getRepositories("octocat")).thenReturn(ResponseEntity.ok(sampleRepos()));
        when(converter.convertUser(sampleUser(), sampleRepos())).thenReturn(sampleDto());

        assertEquals(sampleDto(), service.getUserByIdFailFast("octocat"));

        verify(client, times(1)).getUser("octocat");
        verify(client, times(1)).getRepositories("octocat");
        verify(converter, times(1)).convertUser(sampleUser(), sampleRepos());
    }

    @Test
    void getUserByIdFailFast_FailFirst() {
        when(client.getUser("octocat")).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        when(client.getRepositories("octocat")).thenReturn(ResponseEntity.ok(sampleRepos()));
        when(converter.convertUser(sampleUser(), sampleRepos())).thenReturn(sampleDto());

        assertThrows(EmptyGitHubResponseException.class, ()-> service.getUserByIdFailFast("octocat"));

        verify(client, times(1)).getUser("octocat");
        verify(client, times(0)).getRepositories("octocat");
    }

    @Test
    void getUserByIdFailFast_FailSecond() {
        when(client.getUser("octocat")).thenReturn(ResponseEntity.ok(sampleUser()));
        when(client.getRepositories("octocat")).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        when(converter.convertUser(sampleUser(), sampleRepos())).thenReturn(sampleDto());

        assertThrows(EmptyGitHubResponseException.class, ()-> service.getUserByIdFailFast("octocat"));

        verify(client, times(1)).getUser("octocat");
        verify(client, times(1)).getRepositories("octocat");
    }
}
