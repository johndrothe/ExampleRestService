package work.rothe.branch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class GitHubUser {
    @JsonProperty("login")
    private final String login;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("avatar_url")
    private final String avatarUrl;
    @JsonProperty("location")
    private final String location;
    private final String email;
    private final String url;
    @JsonProperty("created_at")
    private final String createdAt;
}