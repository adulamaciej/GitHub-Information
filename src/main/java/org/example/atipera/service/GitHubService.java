package org.example.atipera.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.atipera.client.GitHubClient;
import org.example.atipera.model.Branch;
import org.example.atipera.model.Commit;
import org.example.atipera.model.Owner;
import org.example.atipera.dto.BranchDto;
import org.example.atipera.dto.RepositoryDto;
import org.example.atipera.dto.RepositoryResponseDto;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubService {

    private final GitHubClient githubClient;

    public List<RepositoryResponseDto> getUserRepositories(String username) {
        log.debug("Getting repositories for user: {}", username);

        List<RepositoryDto> repositories = githubClient.getUserRepositories(username);

        List<RepositoryResponseDto> result = repositories.stream()
                .filter(repo -> !repo.isFork())
                .map(this::mapToRepository)
                .toList();
        log.debug("Returning {} non-fork repositories with branch details for user: {}", result.size(), username);

        return result;
    }

    private RepositoryResponseDto mapToRepository(RepositoryDto gitHubRepo) {
        String ownerLogin = gitHubRepo.getOwner().getLogin();
        String repoName = gitHubRepo.getName();
        log.debug("Mapping repository: {}/{}", ownerLogin, repoName);

        List<BranchDto> branches = githubClient.getRepositoryBranches(ownerLogin, repoName);
        log.debug("Retrieved {} branches for repository: {}/{}", branches.size(), ownerLogin, repoName);

        return RepositoryResponseDto.builder()
                .name(repoName)
                .owner(Owner.builder().login(ownerLogin).build())
                .branches(mapToBranches(branches))
                .build();
    }

    private List<Branch> mapToBranches(List<BranchDto> branchDtos) {
        log.debug("Mapping {} branches to response model", branchDtos.size());

        return branchDtos.stream()
                .map(branch -> Branch.builder()
                        .name(branch.getName())
                        .commit(Commit.builder().sha(branch.getCommit().getSha()).build())
                        .build())
                .toList();
    }
}