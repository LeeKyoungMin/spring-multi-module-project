package com.jobis.refund.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobis.refund.domain.User.dto.SzsUserDto;
import com.jobis.refund.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<SzsUserDto> getUsers(){
        var user = userService.getUsers();
        return ResponseEntity.ok(user);
    }
}
