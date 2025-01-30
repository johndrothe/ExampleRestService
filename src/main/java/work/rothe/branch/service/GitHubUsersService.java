package work.rothe.branch.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import work.rothe.branch.client.GitHubClient;
import work.rothe.branch.converter.GitHubUserConverter;
import work.rothe.branch.dto.UserDto;
import work.rothe.branch.model.GitHubRepo;
import work.rothe.branch.model.GitHubUser;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GitHubUsersService {
    private final GitHubClient client;
    private final GitHubUserConverter converter;
    private final Cache<String, UserDto> cache;

    public GitHubUsersService(GitHubClient client,
                              GitHubUserConverter converter,
                              @Value("${work.rothe.branch.github.cache.initial_size:5000}")
                              int cacheInitialSize,
                              @Value("${work.rothe.branch.github.cache.maximum_size:10000}")
                              int cacheMaximumSize,
                              @Value("${work.rothe.branch.github.cache.ttl-minutes:60}")
                              int cacheTtlMinutes) {
        this.client = client;
        this.converter = converter;
        this.cache = createCache(cacheInitialSize, cacheMaximumSize, cacheTtlMinutes);
    }

    public ResponseEntity<UserDto> getUserByIdFailFast(String userId) {
        return getFromCache(userId)
                .map(this::logCacheHit)
                .map(ResponseEntity::ok)
                .orElseGet(() -> requestUser(userId));
    }

    ResponseEntity<UserDto> requestUser(String userId) {
        val userResponse = client.getUser(userId);
        val reposResponse = client.getRepositories(userId);

        // strategy: If either GitHub request fails, the incoming request fails.
        if (isSuccessful(userResponse, reposResponse)) {
            log.info("Successfully requested user information for userId: {}", userId);
            return toResponse(userResponse.getBody(), reposResponse.getBody());
        }

        val status = getFailedStatus(userResponse, reposResponse);
        log.error("Failed to request user information for userId: {} status:{}", userId, status);
        return ResponseEntity.status(status).build();
    }

    ResponseEntity<UserDto> toResponse(GitHubUser user, List<GitHubRepo> repos) {
        return ResponseEntity.ok(convertAndCacheDto(user, repos));
    }

    private UserDto convertAndCacheDto(GitHubUser user, List<GitHubRepo> repos) {
        val dto = converter.convertUser(user, repos);
        cache.put(user.getLogin().trim().toLowerCase(), dto);
        return dto;
    }

    static boolean isSuccessful(ResponseEntity<?> first, ResponseEntity<?> second) {
        return first.getStatusCode().is2xxSuccessful() && second.getStatusCode().is2xxSuccessful();
    }

    static int getFailedStatus(ResponseEntity<?> first, ResponseEntity<?> second) {
        if (first.getStatusCode().isError()) {
            return first.getStatusCode().value();
        }
        return second.getStatusCode().value();
    }

    private Optional<UserDto> getFromCache(String userId) {
        return Optional.ofNullable(cache.getIfPresent(userId.trim().toLowerCase()));
    }

    private UserDto logCacheHit(UserDto user) {
        log.info("Retrieved user information for {} from cache.", user.getUserName());
        return user;
    }

    private Cache<String, UserDto> createCache(int cacheInitialSize,
                                               int cacheMaximumSize,
                                               int cacheTtlMinutes) {
        log.info("Initializing cache size: {}/{} ttl: {}",
                cacheInitialSize, cacheMaximumSize, cacheTtlMinutes);
        return Caffeine.newBuilder()
                .maximumSize(cacheMaximumSize)
                .initialCapacity(cacheInitialSize)
                .expireAfterWrite(Duration.ofMinutes(cacheTtlMinutes))
                .build();
    }
}
