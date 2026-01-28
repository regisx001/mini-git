package com.regisx001.minigit.storage;

import java.nio.file.Path;

import com.regisx001.minigit.filesystem.FileSystemService;

public class ObjectStore {

    private final Path objectsDir;
    private final FileSystemService fs;

    public ObjectStore(Path objectsDir, FileSystemService fs) {
        this.objectsDir = objectsDir;
        this.fs = fs;
    }

    public byte[] read(String hash) {
        String dir = hash.substring(0, 2);
        String file = hash.substring(2);

        Path objectFile = objectsDir.resolve(dir).resolve(file);
        return fs.readBytes(objectFile);
    }

    public void store(String hash, byte[] data) {
        String dir = hash.substring(0, 2);
        String file = hash.substring(2);

        Path folder = objectsDir.resolve(dir);
        Path objectFile = folder.resolve(file);

        if (!fs.exists(folder)) {
            fs.createDirectory(folder);
        }

        if (!fs.exists(objectFile)) {
            fs.writeBytes(objectFile, data);
        }
    }
}