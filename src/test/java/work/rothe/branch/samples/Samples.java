package work.rothe.branch.samples;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import work.rothe.branch.dto.RepoDto;
import work.rothe.branch.dto.UserDto;
import work.rothe.branch.model.GitHubRepo;
import work.rothe.branch.model.GitHubUser;

import java.util.List;

public class Samples {
    public static ResponseEntity<Object> response200() {
        return ResponseEntity.ok().build();
    }

    public static ResponseEntity<?> response500() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    public static UserDto sampleDto() {
        return UserDto.builder()
                .userName("octocat")
                .displayName("The Octocat")
                .avatar("https://avatars3.githubusercontent.com/u/583231?v=4")
                .geoLocation("San Francisco")
                .email("octocat@github.com")
                .url("https://github.com/octocat")
                .createdAt("2011-01-25 18:44:36")
                .repositoryList(sampleRepoDtos())
                .build();
    }

    public static List<GitHubRepo> sampleRepos() {
        return List.of(
                new GitHubRepo("A", "B"),
                new GitHubRepo("C", "D")
        );
    }

    public static List<RepoDto> sampleRepoDtos() {
        return List.of(
                new RepoDto("A", "B"),
                new RepoDto("C", "D")
        );
    }

    public static GitHubUser sampleUser() {
        return new GitHubUser("octocat",
                "The Octocat",
                "https://avatars3.githubusercontent.com/u/583231?v=4",
                "San Francisco",
                "octocat@github.com",
                "https://github.com/octocat",
                "2011-01-25 18:44:36");
    }

    public static GitHubRepo sampleRepo() {
        return new GitHubRepo("X", "Y");
    }

    public static RepoDto sampleRepoDto() {
        return new RepoDto("X", "Y");
    }
}
