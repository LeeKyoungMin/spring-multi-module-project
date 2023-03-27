package com.jobis.refund.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobis.refund.domain.User.dto.RefundDto;
import com.jobis.refund.domain.User.dto.SzsUserCommand;
import com.jobis.refund.domain.User.dto.SzsUserDto;
import com.jobis.refund.domain.User.dto.SzsUserReadCommand;
import com.jobis.refund.domain.User.dto.SzsUserScrapDto;
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
    public ResponseEntity<SzsUserDto> getUsers(HttpServletRequest request){
        var userDto = userService.getUsers(request);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<SzsUserDto> createUser(@Valid @RequestBody SzsUserCommand szsUserCommand){
        var userDto = userService.createUser(szsUserCommand);
        return ResponseEntity.created(URI.create("/szs/signup")).body(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<SzsUserTokenDto> login(@RequestBody SzsUserDto szsUserDto){
        var loginUser = userService.login(szsUserDto);
        return ResponseEntity.ok(loginUser);
    }

    @PostMapping("/scrap")
    public ResponseEntity<SzsUserScrapDto> getScrap(@Valid @RequestBody SzsUserReadCommand szsUserReadCommand){
        var scrpDto = userService.getScrap(szsUserReadCommand);
        return ResponseEntity.ok(scrpDto);
    }

    @GetMapping(value="/refund")
    public ResponseEntity<RefundDto> gerRefund(HttpServletRequest request) {
        var refundDto = userService.getRefund(request);
        return ResponseEntity.ok(refundDto);
    }
    
}
