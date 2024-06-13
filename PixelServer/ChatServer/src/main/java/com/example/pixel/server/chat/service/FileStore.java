package com.example.pixel.server.chat.service;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileStore {

    private final File dir = new File("uploaded");

    public File getFile(String name) {
        return new File(dir, name);
    }

}
