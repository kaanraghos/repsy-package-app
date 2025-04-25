package com.baser.config;

import com.baser.FileSystemStorageService;
import com.baser.ObjectStorageService;
import com.baser.storage.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

  public static final String FILE_SYSTEM = "file-system";
  public static final String OBJECT_SYSTEM = "object-system";

  @Value("${repsy.app.storageStrategy:file-system}")
  private String storageStrategy;

  @Value("${repsy.app.storage.baseDir:/data/storage}")
  private String baseDir;

  @Value("${repsy.app.storage.minio.bucketName:/package-storage}")
  private String bucketName;
  @Value("${repsy.app.storage.minio.url:http://localhost:9000}")
  private String url;
  @Value("${repsy.app.storage.minio.key:minioadmin}")
  private String key;
  @Value("${repsy.app.storage.minio.secret:minioadmin}")
  private String secret;

  @Bean
  public StorageService storageService() {
    switch (storageStrategy){
      case FILE_SYSTEM :
        return new FileSystemStorageService(baseDir);
      case OBJECT_SYSTEM :
        return new ObjectStorageService(bucketName, url, key, secret);
    }
    throw new IllegalArgumentException("Unsupported storage strategy: " + storageStrategy);
  }
}
