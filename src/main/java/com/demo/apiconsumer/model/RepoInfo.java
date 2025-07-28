package com.demo.apiconsumer.model;

import java.util.List;

public record Repository(
        String name,
        Owner owner,
        boolean fork,
        List<Branch> branches // This will be populated by a separate API call
) {
}