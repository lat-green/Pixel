package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.dto.StringDTO;
import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.service.FileStore;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@AllArgsConstructor
@RestController
public class FileUploadController {

    private final FileStore tempDir;

    @PostMapping("/upload")
    public StringDTO handleFileUpload(@RequestParam("name") String name, @RequestParam("type") String type, @RequestParam("file") MultipartFile file, Customer user) throws IOException {
        var n = user.getId() + "_" + name;
        var dir = tempDir.getFile(n);
        dir.getParentFile().mkdirs();
        var bytes = file.getBytes();
        try (var out = new BufferedOutputStream(new FileOutputStream(dir))) {
            out.write(bytes);
        }
        return new StringDTO("/load/" + n + "?type=" + type);
    }

}