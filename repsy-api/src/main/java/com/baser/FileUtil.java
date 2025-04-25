package com.baser;

import com.baser.entity.MetaFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FileUtil {
    private final ObjectMapper objectMapper;


    public FileUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public <T> T read(InputStream metaFileInputStream, Class<T> metaFileClass) throws IOException {
        return objectMapper.readValue(metaFileInputStream, metaFileClass);
    }
}
