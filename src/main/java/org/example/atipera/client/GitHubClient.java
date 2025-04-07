package org.example.atipera.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.atipera.exception.UserNotFoundException;
import org.example.atipera.dto.BranchDto;
import org.example.atipera.dto.RepositoryDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class GitHubClient {

    private final RestClient githubRestClient;

    public List<RepositoryDto> getUserRepositories(String username) {
        log.debug("Fetching repositories for user: {}", username);

        try {
            RepositoryDto[] repositories = githubRestClient.get()
                    .uri("/users/{username}/repos", username)
                    .retrieve()
                    .body(RepositoryDto[].class);

            return Optional.ofNullable(repositories)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } catch (HttpClientErrorException.NotFound e) {
            log.error("User not found: {}", username, e);
            throw new UserNotFoundException("User not found: " + username);
        } catch (Exception e) {
            log.error("Error while fetching repositories for user: {}", username, e);
            throw e;
        }
    }


    public List<BranchDto> getRepositoryBranches(String owner, String repo) {
        log.debug("Fetching branches for repository: {}/{}", owner, repo);

        try {
            BranchDto[] branches = githubRestClient.get()
                    .uri("/repos/{owner}/{repo}/branches", owner, repo)
                    .retrieve()
                    .body(BranchDto[].class);

            return Optional.ofNullable(branches)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } catch (Exception e) {
            log.error("Error while fetching branches for repository: {}/{}", owner, repo, e);
            return Collections.emptyList();
        }
    }
}