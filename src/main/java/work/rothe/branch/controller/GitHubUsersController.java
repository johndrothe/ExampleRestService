package work.rothe.branch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.rothe.branch.dto.UserDto;
import work.rothe.branch.service.GitHubUsersService;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class GitHubUsersController {
    private final GitHubUsersService usersService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        log.debug("User information request for {}", userId);
        return ResponseEntity.ok(usersService.getUserByIdFailFast(userId));
    }
}
