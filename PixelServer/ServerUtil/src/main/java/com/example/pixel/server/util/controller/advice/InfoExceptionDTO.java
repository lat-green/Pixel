package com.example.pixel.server.util.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Builder
@Data
public final class InfoExceptionDTO {

    final Date date = new Date();
    String type;
    String info;
    String description;

}