package com.demo.apiconsumer.service;


import com.demo.apiconsumer.exception.GithubUserNotFoundException;
import com.demo.apiconsumer.model.Branch;
import com.demo.apiconsumer.model.RepoInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class GithubService {
    private final RestClient githubRestClient;

    public GithubService(@Qualifier("githubRestClient") RestClient githubRestClient) {
        this.githubRestClient = githubRestClient;
    }

    public List<RepoInfo> getUserRepositories(String username) {
        List<RepoInfo> repositories = githubRestClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    if (response.getStatusCode().equals(HttpStatusCode.valueOf(404))) {
                        throw new GithubUserNotFoundException("GitHub user '" + username + "' not found.");
                    }
                    throw new RuntimeException("Client error accessing GitHub API: " + response.getStatusCode());
                })
                .body(new ParameterizedTypeReference<List<RepoInfo>>() {
                });

        if (repositories == null) {
            return new ArrayList<>();
        }

        return repositories.parallelStream()
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    List<Branch> branches = getRepositoryBranches(repo.owner().login(), repo.name());
                    return new RepoInfo(repo.name(), repo.owner(), repo.fork(), branches);
                })
                .toList();
    }

    private List<Branch> getRepositoryBranches(String owner, String repoName) {
        List<Branch> branches = githubRestClient.get()
                .uri("/repos/{owner}/{repoName}/branches", owner, repoName)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Branch>>() {
                });
        return branches != null ? branches : new ArrayList<>();
    }

}
