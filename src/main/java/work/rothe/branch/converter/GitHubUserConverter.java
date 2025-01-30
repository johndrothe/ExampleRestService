package work.rothe.branch.converter;

import org.springframework.stereotype.Component;
import work.rothe.branch.dto.RepoDto;
import work.rothe.branch.dto.UserDto;
import work.rothe.branch.model.GitHubRepo;
import work.rothe.branch.model.GitHubUser;

import java.util.List;

@Component
public class GitHubUserConverter {
    public UserDto convertUser(GitHubUser gitHubUser, List<GitHubRepo> gitHubRepos) {
        return UserDto.builder()
                .userName(gitHubUser.getLogin())
                .displayName(gitHubUser.getName())
                .avatar(gitHubUser.getAvatarUrl())
                .geoLocation(gitHubUser.getLocation())
                .email(gitHubUser.getEmail())
                .url(gitHubUser.getUrl())
                .createdAt(gitHubUser.getCreatedAt())
                .repositoryList(convertRepos(gitHubRepos))
                .build();
    }

    public List<RepoDto> convertRepos(List<GitHubRepo> gitHubRepos) {
        return gitHubRepos.stream()
                .map(this::convertRepo)
                .toList();
    }

    public RepoDto convertRepo(GitHubRepo gitHubRepo) {
        return new RepoDto(gitHubRepo.name(), gitHubRepo.url());
    }
}
