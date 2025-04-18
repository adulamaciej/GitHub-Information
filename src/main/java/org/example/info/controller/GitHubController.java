package org.example.info.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.info.dto.RepositoryResponseDto;
import org.example.info.service.GitHubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repositories")
@RequiredArgsConstructor
public class GitHubController {

    private final GitHubService githubService;

    @GetMapping("/{username}")
    public ResponseEntity<List<RepositoryResponseDto>> getUserRepositories(@PathVariable String username) {
        List<RepositoryResponseDto> repositories = githubService.getUserRepositories(username);
        return ResponseEntity.ok(repositories);
    }
}
