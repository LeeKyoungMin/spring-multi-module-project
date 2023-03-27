package com.jobis.refund.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobis.refund.domain.User.dto.SzsUserCommand;
import com.jobis.refund.domain.User.dto.SzsUserDto;
import com.jobis.refund.domain.User.dto.SzsUserTokenDto;
import com.jobis.refund.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<SzsUserDto> getUsers(){
        var userDto = userService.getUsers();
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<SzsUserDto> createUser(@RequestBody SzsUserCommand command){
        var userDto = userService.createUser(command);
        return ResponseEntity.created(URI.create("/szs/signup")).body(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<SzsUserTokenDto> login(@RequestBody SzsUserDto szsUserDto){
        var loginUser = userService.login(szsUserDto);
        return ResponseEntity.ok(loginUser);
    }
}
