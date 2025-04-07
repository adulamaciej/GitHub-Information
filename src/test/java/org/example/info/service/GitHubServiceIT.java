package org.example.info.service;

import org.example.info.client.GitHubClient;
import org.example.info.dto.RepositoryResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import org.example.info.dto.RepositoryDto;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class GitHubServiceIT {

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private GitHubClient gitHubClient;

    @Test
    public void shouldRetrieveRepositoriesForExistingUser() {

        // Given
        String username = "octocat";

        // When
        List<RepositoryResponseDto> repositories = gitHubService.getUserRepositories(username);

        // Then
        assertThat(repositories).isNotEmpty();

        repositories.forEach(repo -> {
            assertThat(repo.getName()).isNotNull();
            assertThat(repo.getOwner().getLogin()).isEqualTo(username);
            assertThat(repo.getBranches()).isNotNull();
            assertThat(repo.getBranches()).isNotEmpty();
        });
    }

    @Test
    public void shouldFilterOutForkRepositories() {
        // Given
        String username = "octocat";

        // When
        List<RepositoryDto> repos = gitHubClient.getUserRepositories(username);
        List<RepositoryResponseDto> filteredRepositories = gitHubService.getUserRepositories(username);

        // Then
        List<String> forkNames = repos.stream()
                .filter(RepositoryDto::isFork)
                .map(RepositoryDto::getName)
                .toList();

        List<String> filteredNames = filteredRepositories.stream()
                .map(RepositoryResponseDto::getName)
                .toList();

        assertThat(forkNames).isNotEmpty();

        for (String forkName : forkNames) {
            assertThat(filteredNames).doesNotContain(forkName);
        }
    }

    @Test
    public void shouldHandleUsernameWithSpaces() {
        // Given
        String usernameWithSpaces = " octocat ";

        // When
        List<RepositoryResponseDto> repositories = gitHubService.getUserRepositories(usernameWithSpaces.trim());

        // Then
        assertThat(repositories).isNotEmpty();
        repositories.forEach(repo -> assertThat(repo.getOwner().getLogin()).isEqualTo("octocat"));
    }
}