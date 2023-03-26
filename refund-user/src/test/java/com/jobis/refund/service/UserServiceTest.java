package com.jobis.refund.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jobis.refund.config.userlist.SzsUserList;
import com.jobis.refund.domain.User.dto.SzsUserCommand;
import com.jobis.refund.domain.User.dto.SzsUserDto;
import com.jobis.refund.domain.User.entity.SzsUser;
import com.jobis.refund.repository.user.SzsUserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private SzsUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void init(){
        SzsUserList.getNames().add("홍길동");
        SzsUserList.getRegNos().add("860824-1655068");
    }

    @Test
    @DisplayName("[Service] 유저 회원가입시 리스트 외 가입 Exception 테스트")
    void testCreateUser_Exception() {
        SzsUserCommand command = new SzsUserCommand("test", "1234", "홍순동", "860824-1655068");

        assertThrows(RuntimeException.class, () -> userService.createUser(command));
    }

    @Test
    @DisplayName("[Service] 회원가입 테스트")
    void testCreateUser(){
        SzsUserCommand command = new SzsUserCommand("hong", "1234", "홍길동", "860824-1655068");

        SzsUser szsUser = SzsUser.builder().id("test").name("홍길동").userId("hong").password("1234").regNo("860824-1655068").build();

        when(userRepository.findByUserId(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(SzsUser.class))).thenReturn(szsUser);
        when(passwordEncoder.encode(anyString())).thenReturn("test_encoded_password");

        SzsUserDto szsUserDto = userService.createUser(command);

        assertEquals(szsUser.getUserId(), szsUserDto.getUserId());
        assertEquals(szsUser.getName(), szsUserDto.getName());
        assertEquals(szsUser.getPassword(), szsUserDto.getPassword());
    }

    @Test
    void testGetUsers() {

    }
}
