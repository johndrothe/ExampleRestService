package work.rothe.branch.model;

import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public record GitHubRepo(String name, String url){
    public static final ParameterizedTypeReference<List<GitHubRepo>> TYPE_REPO_LIST = new ParameterizedTypeReference<>() {
    };
}
