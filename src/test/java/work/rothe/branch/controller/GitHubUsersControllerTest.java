package work.rothe.branch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import work.rothe.branch.dto.UserDto;
import work.rothe.branch.service.GitHubUsersService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GitHubUsersControllerTest {
    private final GitHubUsersService usersService = mock(GitHubUsersService.class);
    private final GitHubUsersController controller = new GitHubUsersController(usersService);

    @Test
    void getUserById() {
        when(usersService.getUserByIdFailFast("octocat"))
                .thenReturn(ResponseEntity.ok(sampleDto()));

        assertEquals(ResponseEntity.ok(sampleDto()), controller.getUserById("octocat"));
        verify(usersService, times(1)).getUserByIdFailFast("octocat");
    }

    private static UserDto sampleDto() {
        return UserDto.builder()
                .userName("octocat")
                .displayName("The Octocat")
                .avatar("https://avatars3.githubusercontent.com/u/583231?v=4")
                .geoLocation("San Francisco")
                .email("octocat@github.com")
                .url("https://github.com/octocat")
                .createdAt("2011-01-25 18:44:36")
                .repositoryList(List.of())
                .build();
    }
}
