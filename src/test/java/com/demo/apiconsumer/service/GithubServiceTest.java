package com.demo.apiconsumer.service;

import com.demo.apiconsumer.model.Branch;
import com.demo.apiconsumer.model.RepoInfo;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GithubServiceTest {

    private GithubService githubService;

    @BeforeEach
    void setUp() {
        WireMockServer wireMockServer = new WireMockServer(8089);
        wireMockServer.start();

        // Stub /users/{username}/repos
        wireMockServer.stubFor(get(urlEqualTo("/users/testuser/repos"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/vnd.github+json")
                        .withBody("""
                                [
                                  {
                                    "name": "repo1",
                                    "owner": { "login": "testuser" },
                                    "fork": false
                                  },
                                  {
                                    "name": "repo2",
                                    "owner": { "login": "testuser" },
                                    "fork": true
                                  }
                                ]
                                """)));

        // Stub /repos/{owner}/{repoName}/branches
        wireMockServer.stubFor(get(urlEqualTo("/repos/testuser/repo1/branches"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/vnd.github+json")
                        .withBody("""
                                [
                                  { "name": "main", "commit": { "sha": "abc123" } }
                                ]
                                """)));

        // Use RestClient with WireMock base URL
        RestClient restClient = RestClient.builder()
                .baseUrl("http://localhost:8089")
                .build();
        githubService = new GithubService(restClient);
    }

    @Test
    void getUserRepositories_happyPath_returnsNonForkReposWithBranches() {
        List<RepoInfo> repos = githubService.getUserRepositories("testuser");

        assertThat(repos).hasSize(1);
        RepoInfo repo = repos.getFirst();
        assertThat(repo.name()).isEqualTo("repo1");
        assertThat(repo.owner().login()).isEqualTo("testuser");
        assertThat(repo.fork()).isFalse();
        assertThat(repo.branches()).hasSize(1);
        Branch branch = repo.branches().getFirst();
        assertThat(branch.name()).isEqualTo("main");
        assertThat(branch.commit().sha()).isEqualTo("abc123");
    }
}