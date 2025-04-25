package com.baser.controller;


import com.baser.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/packages")
public class PackageController {

    private final PackageService packageService;

    @Autowired
    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping(path = "/deploy/{packageName}/{version}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(
            @PathVariable("packageName") String packageName,
            @PathVariable("version") String version,
            @RequestPart("packageFile") MultipartFile packageFile,
            @RequestPart("metaFile") MultipartFile metaFile) throws IOException {

        packageService.savePackage(packageName, version, packageFile, metaFile);
    }


    @GetMapping("/download/{packageName}/{version}/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(
            @PathVariable("packageName") String packageName,
            @PathVariable("version") String version,
            @PathVariable("fileName") String fileName) {
        InputStream file = packageService.load(packageName, version, fileName);
        InputStreamResource resource = new InputStreamResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // or other specific type if needed
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

}
