package com.baser;

import com.baser.storage.StorageService;
import com.baser.storage.exception.StorageException;
import io.minio.*;

import java.io.InputStream;

public class ObjectStorageService implements StorageService {

    private final MinioClient minioClient;
    private final String bucketName;

    public ObjectStorageService(String bucketName, String url, String key, String secret) {
        this.bucketName = bucketName;
        this.minioClient = MinioClient.builder()
                .endpoint(url)
                .credentials(key, secret)
                .build();

        try {
            ensureBucketExists();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during bucket control", e);
        }
    }

    private void ensureBucketExists() throws Exception {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }


    @Override
    public void save(String packageName, String version, String fileName, InputStream content) throws StorageException {
        String objectName = packageName + "/" + version + "/" + fileName;
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(content, -1, 10485760) // 10MB parça boyutu
                            .contentType("application/octet-stream")
                            .build()
            );
        } catch (Exception e) {
            throw new StorageException("File couldn't saved: " + objectName, e);
        }
    }

    @Override
    public InputStream load(String packageName, String version, String fileName) throws StorageException {
        String objectName = packageName + "/" + version + "/" + fileName;
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new StorageException("File couldn't get: " + objectName, e);
        }
    }
}