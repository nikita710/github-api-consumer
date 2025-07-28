package com.demo.apiconsumer.controller;


import com.demo.apiconsumer.model.RepoInfo;
import com.demo.apiconsumer.service.GithubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/github")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<List<RepoInfo>> getUserRepositories(@PathVariable String username) {
        List<RepoInfo> repositories = githubService.getUserRepositories(username);
        return ResponseEntity.ok(repositories);
    }
}