package org.example.info.client;


import org.example.info.dto.BranchDto;
import org.example.info.dto.RepositoryDto;
import org.example.info.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class GitHubClientIT {

        @Autowired
        private GitHubClient gitHubClient;

        @Test
        public void shouldFetchRepositoriesForExistingUser() {
            // Given
            String username = "torvalds";

            // When
            List<RepositoryDto> repositories = gitHubClient.getUserRepositories(username);

            // Then
            assertThat(repositories).isNotEmpty();

            repositories.forEach(repo -> {
                assertThat(repo.getName()).isNotNull();
                assertThat(repo.getOwner()).isNotNull();
                assertThat(repo.getOwner().getLogin()).isEqualTo(username);
            });
        }

        @Test
        public void shouldFetchBranchesForExistingRepository() {
            // Given
            String owner = "torvalds";
            String repoName = "linux";

            // When
            List<BranchDto> branches = gitHubClient.getRepositoryBranches(owner, repoName);

            // Then
            assertThat(branches).isNotEmpty();

            branches.forEach(branch -> {
                assertThat(branch.getName()).isNotNull();
                assertThat(branch.getCommit()).isNotNull();
                assertThat(branch.getCommit().getSha()).isNotNull();
            });
        }
    @Test
    public void shouldThrowExceptionForNonExistentUser() {
        // Given
        String username = "this-user-definitely-does-not-exist-12345678900987654321";

        // When & Then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> gitHubClient.getUserRepositories(username));

        assertTrue(exception.getMessage().contains(username));
    }
}
