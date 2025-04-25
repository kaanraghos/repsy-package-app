package com.baser.config;

import com.baser.FileSystemStorageService;
import com.baser.storage.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

  public static final String FILE_SYSTEM = "file-system";

  @Value("${repsy.app.storageStrategy:file-system}")
  private String storageStrategy;

  @Value("${repsy.app.storage.baseDir:/data/storage}")
  private String baseDir;

  @Bean
  public StorageService storageService() {
    if (FILE_SYSTEM.equalsIgnoreCase(storageStrategy)) {
      return new FileSystemStorageService(baseDir);
    }
    throw new IllegalArgumentException("Unsupported storage strategy: " + storageStrategy);
  }
}
