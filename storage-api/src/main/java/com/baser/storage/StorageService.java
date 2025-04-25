package com.baser.storage;

import com.baser.storage.exception.StorageException;

import java.io.InputStream;

public interface StorageService {
    void save(String packageName, String version, String fileName, InputStream content) throws StorageException;
    InputStream load(String packageName, String version, String fileName) throws StorageException;
}