package com.demo.apiconsumer.model;

public record Branch(
        String name,
        Commit commit
) {
}