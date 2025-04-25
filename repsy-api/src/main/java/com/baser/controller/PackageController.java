package com.baser.controller;


import com.baser.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@RestController
@RequestMapping("/packages")
public class PackageController {

    private final StorageService storageService;

    @Autowired
    public PackageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/{packageName}/{version}/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(
            @PathVariable("packageName") String packageName,
            @PathVariable("version") String version,
            @PathVariable("fileName") String fileName) {
        InputStream file = storageService.load(packageName, version, fileName);
        InputStreamResource resource = new InputStreamResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // or other specific type if needed
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

}
