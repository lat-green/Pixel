package com.example.pixel.server.chat.controller.advice.exception;

public class VideoAlreadyVerified extends RuntimeException {

    public VideoAlreadyVerified(Long userId, Long videoId) {
        super("user = " + userId + " video = " + videoId);
    }

}
