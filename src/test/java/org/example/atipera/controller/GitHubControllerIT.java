package org.example.atipera.controller;

import org.example.atipera.dto.RepositoryResponseDto;
import org.example.atipera.exception.UserNotFoundException;
import org.example.atipera.service.GitHubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;


@SpringBootTest
public class GitHubControllerIT {

    @Autowired
    private GitHubController gitHubController;

    @Autowired
    private GitHubService gitHubService;

    @Test
    public void shouldReturnRepositoriesForExistingUser() {
        // Given
        String username = "octocat";

        // When
        ResponseEntity<List<RepositoryResponseDto>> response = gitHubController.getUserRepositories(username);

        // Then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();

        List<RepositoryResponseDto> serviceResults = gitHubService.getUserRepositories(username);
        assertThat(response.getBody()).hasSameSizeAs(serviceResults);
    }

    @Test
    public void shouldThrow404ForNonExistentUser() {
        // Given
        String username = "this-user-definitely-does-not-exist-12345678900987654321";

        // When & Then
        assertThrows(UserNotFoundException.class, () -> gitHubController.getUserRepositories(username));
    }
}