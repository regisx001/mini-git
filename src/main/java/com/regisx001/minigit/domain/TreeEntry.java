package com.regisx001.minigit.domain;

public class TreeEntry {

    private final String mode;
    private final String name;
    private final String hash;

    public TreeEntry(String mode, String name, String hash) {
        this.mode = mode;
        this.name = name;
        this.hash = hash;
    }

    public String serialize() {
        return mode + " " + name + " " + hash;
    }
}
