package com.regisx001.minigit.domain;

import java.nio.charset.StandardCharsets;

import com.regisx001.minigit.utils.HashUtil;

public class Blob {

    private final byte[] content;

    public Blob(byte[] content) {
        this.content = content;
    }

    public byte[] serialize() {
        String header = "blob " + content.length + "\0";
        byte[] headerBytes = header.getBytes(StandardCharsets.UTF_8);

        byte[] result = new byte[headerBytes.length + content.length];
        System.arraycopy(headerBytes, 0, result, 0, headerBytes.length);
        System.arraycopy(content, 0, result, headerBytes.length, content.length);
        return result;
    }

    public String hash() {
        return HashUtil.sha1(serialize());
    }
}