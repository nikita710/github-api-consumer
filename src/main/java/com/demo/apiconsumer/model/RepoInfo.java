package com.demo.apiconsumer.model;

import java.util.List;

public record RepoInfo(
        String name,
        Owner owner,
        boolean fork,
        List<Branch> branches
) {
}