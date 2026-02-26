package com.regisx001.minigit.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IgnoreService {

    private final Set<String> ignored = new HashSet<>();

    public IgnoreService() {
        Path ignoreFile = Path.of(".minigitignore");

        if (Files.exists(ignoreFile)) {
            try {
                List<String> lines = Files.readAllLines(ignoreFile);
                for (String line : lines) {
                    String trimmed = line.trim();
                    if (!trimmed.isBlank() && !trimmed.startsWith("#")) {
                        ignored.add(trimmed);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to read .minigitignore", e);
            }
        }
    }

    public boolean isIgnored(String path) {
        for (String pattern : ignored) {
            String normalizedPattern = pattern;
            boolean isRoot = pattern.startsWith("/");
            if (isRoot) {
                normalizedPattern = pattern.substring(1);
            }
            
            if (normalizedPattern.endsWith("/")) {
                normalizedPattern = normalizedPattern.substring(0, normalizedPattern.length() - 1);
            }

            if (normalizedPattern.startsWith("*")) {
                String suffix = normalizedPattern.substring(1);
                if (path.endsWith(suffix)) {
                    return true;
                }
            } else if (isRoot) {
                if (path.equals(normalizedPattern) || path.startsWith(normalizedPattern + "/")) {
                    return true;
                }
            } else {
                if (path.equals(normalizedPattern) || 
                    path.startsWith(normalizedPattern + "/") || 
                    path.endsWith("/" + normalizedPattern) || 
                    path.contains("/" + normalizedPattern + "/")) {
                    return true;
                }
            }
        }
        return false;
    }
}