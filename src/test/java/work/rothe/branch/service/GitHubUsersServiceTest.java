package work.rothe.branch.service;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import work.rothe.branch.client.GitHubClient;
import work.rothe.branch.converter.GitHubUserConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static work.rothe.branch.samples.Samples.response200;
import static work.rothe.branch.samples.Samples.response500;
import static work.rothe.branch.samples.Samples.sampleDto;
import static work.rothe.branch.samples.Samples.sampleRepos;
import static work.rothe.branch.samples.Samples.sampleUser;

public class GitHubUsersServiceTest {
    private final GitHubClient client = mock(GitHubClient.class);
    private final GitHubUserConverter converter = mock(GitHubUserConverter.class);
    private final GitHubUsersService service = new GitHubUsersService(client, converter, 50, 100, 60);

    @Test
    void getUserByIdFailFast_Success() {
        when(client.getUser("octocat")).thenReturn(ResponseEntity.ok(sampleUser()));
        when(client.getRepositories("octocat")).thenReturn(ResponseEntity.ok(sampleRepos()));
        when(converter.convertUser(sampleUser(), sampleRepos())).thenReturn(sampleDto());

        assertEquals(ResponseEntity.ok(sampleDto()), service.getUserByIdFailFast("octocat"));

        verify(client, times(1)).getUser("octocat");
        verify(client, times(1)).getRepositories("octocat");
        verify(converter, times(1)).convertUser(sampleUser(), sampleRepos());
    }

    @Test
    void getUserByIdFailFast_FailFirst() {
        when(client.getUser("octocat")).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        when(client.getRepositories("octocat")).thenReturn(ResponseEntity.ok(sampleRepos()));
        when(converter.convertUser(sampleUser(), sampleRepos())).thenReturn(sampleDto());

        assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).build(), service.getUserByIdFailFast("octocat"));

        verify(client, times(1)).getUser("octocat");
        verify(client, times(1)).getRepositories("octocat");
    }

    @Test
    void getUserByIdFailFast_FailSecond() {
        when(client.getUser("octocat")).thenReturn(ResponseEntity.ok(sampleUser()));
        when(client.getRepositories("octocat")).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        when(converter.convertUser(sampleUser(), sampleRepos())).thenReturn(sampleDto());

        assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).build(), service.getUserByIdFailFast("octocat"));

        verify(client, times(1)).getUser("octocat");
        verify(client, times(1)).getRepositories("octocat");
    }

    @Test
    void toResponse() {
        when(converter.convertUser(eq(sampleUser()), eq(sampleRepos()))).thenReturn(sampleDto());

        assertEquals(ResponseEntity.ok(sampleDto()), service.toResponse(sampleUser(), sampleRepos()));

        verify(converter, times(1)).convertUser(eq(sampleUser()), eq(sampleRepos()));
    }

    @Test
    void isSuccessful() {
        assertTrue(GitHubUsersService.isSuccessful(response200(), response200()));
        assertFalse(GitHubUsersService.isSuccessful(response200(), response500()));
        assertFalse(GitHubUsersService.isSuccessful(response500(), response500()));
        assertFalse(GitHubUsersService.isSuccessful(response500(), response200()));
    }

    @Test
    void getFailedStatus() {
        assertEquals(500, GitHubUsersService.getFailedStatus(response500(), response200()));
        assertEquals(500, GitHubUsersService.getFailedStatus(response200(), response500()));
    }
}
