package com.baser;

import com.baser.storage.StorageService;
import com.baser.storage.exception.StorageException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileSystemStorageService implements StorageService {

    private final Path basePath;

    public FileSystemStorageService(String baseDir) {
        this.basePath = Paths.get(baseDir);
        try {
            Files.createDirectories(this.basePath);
        } catch (IOException e) {
            throw new StorageException("Failed to create base directory: " + baseDir, e);
        }
    }

    @Override
    public void save(String packageName, String version, String fileName, InputStream content) {
        try {
            Path dir = basePath.resolve(packageName).resolve(version);
            Files.createDirectories(dir);
            Path filePath = dir.resolve(fileName);
            Files.copy(content, filePath);
            log.info("Saved file to {}", filePath);
        } catch (IOException e) {
            throw new StorageException("Could not save file: " + fileName, e);
        }
    }

    @Override
    public InputStream load(String packageName, String version, String fileName) {
        try {
            Path filePath = basePath.resolve(packageName).resolve(version).resolve(fileName);
            if (!Files.exists(filePath)) {
                throw new StorageException("File not found: " + filePath);
            }
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            throw new StorageException("Could not read file: " + fileName, e);
        }
    }
}