package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.controller.securiry.HasScopeProfileWrite;
import com.example.pixel.server.chat.dto.user.CustomerReplaceRequest;
import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UtilController {

    private final UserService userService;

    @HasScopeProfileWrite
    @PutMapping("/users")
    public Customer replaceUser(Customer user, @RequestBody CustomerReplaceRequest replaceRequest) {
        return userService.replaceUser(user, replaceRequest);
    }

}
