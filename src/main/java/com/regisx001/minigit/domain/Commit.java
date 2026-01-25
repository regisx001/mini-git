package com.regisx001.minigit.domain;

import java.nio.charset.StandardCharsets;

import com.regisx001.minigit.utils.HashUtil;

public class Commit {

    private final String treeHash;
    private final String parentHash;
    private final String author;
    private final long timestamp;
    private final String message;

    public Commit(String treeHash, String parentHash,
            String author, long timestamp, String message) {
        this.treeHash = treeHash;
        this.parentHash = parentHash;
        this.author = author;
        this.timestamp = timestamp;
        this.message = message;
    }

    public byte[] serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("tree ").append(treeHash).append("\n");
        if (parentHash != null) {
            sb.append("parent ").append(parentHash).append("\n");
        }
        sb.append("author ").append(author)
                .append(" ").append(timestamp).append("\n\n");
        sb.append(message);

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public String hash() {
        return HashUtil.sha1(serialize());
    }
}
