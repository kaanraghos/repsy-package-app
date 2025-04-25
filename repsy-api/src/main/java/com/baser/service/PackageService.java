package com.baser.service;

import com.baser.FileUtil;
import com.baser.MetaFileRepository;
import com.baser.entity.MetaFile;
import com.baser.storage.StorageService;
import com.baser.storage.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class PackageService {

    private final StorageService storageService;
    private final FileUtil fileUtil;
    private final MetaFileRepository repository;

    @Autowired
    public PackageService(StorageService storageService, FileUtil fileUtil, MetaFileRepository repository) {
        this.storageService = storageService;
        this.fileUtil = fileUtil;
        this.repository = repository;
    }

    public void savePackage(String packageName, String version, MultipartFile packageFile, MultipartFile metaFile) throws IOException {
        MetaFile file = fileUtil.read(metaFile.getInputStream(), MetaFile.class);
        if (!file.getName().equals(packageName)) {
            throw new StorageException("Package name does not match with metafile");
        }
        if (!file.getVersion().equals(version)) {
            throw new StorageException("Package version does not match with metafile");
        }
        repository.save(file);
        storageService.save(packageName, version, "meta.json", metaFile.getInputStream());
        storageService.save(packageName, version, "package.rep", packageFile.getInputStream());
    }

    public InputStream load(String packageName, String version, String fileName) {
        return storageService.load(packageName, version, fileName);
    }
}
