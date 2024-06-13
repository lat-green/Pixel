package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.service.FileStore;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@AllArgsConstructor
public class FileLoadController {

    private final FileStore store;

    @GetMapping(value = "/load/{file}")
    public ResponseEntity<byte[]> handleFileLoad(
            @PathVariable("file") String fileName,
            @RequestParam(value = "type", required = false, defaultValue = "application/octet-stream") String fileType
    ) throws IOException {
        var file = store.getFile(fileName);
        final byte[] bytes;
        try (var in = new BufferedInputStream(new FileInputStream(file))) {
            bytes = in.readAllBytes();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileType))
                .body(bytes);
    }

}
