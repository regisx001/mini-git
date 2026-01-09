package com.regisx001.minigit.domain;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.regisx001.minigit.utils.HashUtil;

public class Tree {

    private final List<TreeEntry> entries;

    public Tree(List<TreeEntry> entries) {
        this.entries = entries;
    }

    public byte[] serialize() {
        String body = entries.stream()
                .map(TreeEntry::serialize)
                .reduce("", (a, b) -> a + b + "\n");

        String header = "tree " + body.length() + "\0";
        return (header + body).getBytes(StandardCharsets.UTF_8);
    }

    public String hash() {
        return HashUtil.sha1(serialize());
    }
}