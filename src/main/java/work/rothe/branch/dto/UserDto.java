package work.rothe.branch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class UserDto {
    @JsonProperty("user_name")
    private final String userName;

    @JsonProperty("display_name")
    private final String displayName;
    private final String avatar;
    @JsonProperty("geo_location")
    private final String geoLocation;
    private final String email;
    private final String url;
    @JsonProperty("created_at")
    private final String createdAt;
    @JsonProperty("repos")
    private final List<RepoDto> repositoryList;
}
