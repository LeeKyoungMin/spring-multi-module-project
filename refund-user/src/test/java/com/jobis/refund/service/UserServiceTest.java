package com.jobis.refund.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jobis.refund.config.userlist.SzsUserList;
import com.jobis.refund.domain.User.dto.RefundDto;
import com.jobis.refund.domain.User.dto.SzsUserCommand;
import com.jobis.refund.domain.User.dto.SzsUserDto;
import com.jobis.refund.domain.User.dto.SzsUserReadCommand;
import com.jobis.refund.domain.User.dto.SzsUserScrapDto;
import com.jobis.refund.domain.User.dto.SzsUserTokenDto;
import com.jobis.refund.domain.User.entity.Deduction;
import com.jobis.refund.domain.User.entity.Salary;
import com.jobis.refund.domain.User.entity.SzsUser;
import com.jobis.refund.domain.User.entity.TaxAmount;
import com.jobis.refund.repository.deduction.DeductionRepository;
import com.jobis.refund.repository.salary.SalaryRepository;
import com.jobis.refund.repository.taxAmount.TaxAmountRepository;
import com.jobis.refund.repository.user.SzsUserRepository;
import com.jobis.refund.security.jwt.JwtProvider;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private SzsUserRepository userRepository;

    @Mock
    private SalaryRepository salaryRepository;

    @Mock
    private TaxAmountRepository taxAmountRepository;

    @Mock
    private DeductionRepository deductionRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    private MockHttpServletRequest request;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void init(){
        SzsUserList.getNames().add("홍길동");
        SzsUserList.getRegNos().add("860824-1655068");
        request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1c2VySWQiOiJob25nIiwic3ViIjoidXNlciIsImlhdCI6MTY3OTkyODc1NiwiZXhwIjoxNjc5OTMwNTU2fQ.Rihhigpz3uBWFl9BaNriqW6CYCQV4we7GQzmOAZPmIEJ3oiMGK1AiYVkXdmHySlbbf4ugAslXqcY4jjfM8JwiNlaT7FeTMht0WOIyC_QdTgOln1_0sXSpKjpye_yDlOIw8f11FxefkGfq4-Va4U9zAlDN7pOg2WrVzmlFjKpSO5t1cG-FMnIYjhfmwhCcoy3oLRdFA4EUUXjuDkKBQ-H7M2QOWxQWlsdI7Ubk4vYxqsAbKpyrw8RYPh0__uVBcbzL-yn08KjaStCH2mjjzr5ArsSi7QcbQg18G0gonGXJx9ZOxrGERwEBYEubU5eozuZYQ1801gdZn6AziJuVdMxyg");
    }

    @Test
    @DisplayName("[Service] 유저 회원가입시 리스트 외 가입 Exception 테스트")
    void createUser_Exception_Test() {
        SzsUserCommand command = new SzsUserCommand("test", "1234", "홍순동", "860824-1655068");

        assertThrows(RuntimeException.class, () -> userService.createUser(command));
    }

    @Test
    @DisplayName("[Service] 회원가입 테스트")
    void createUserTest(){

        //given
        SzsUserCommand command = new SzsUserCommand("hong", "1234", "홍길동", "860824-1655068");

        SzsUser szsUser = SzsUser.builder().id("test").name("홍길동").userId("hong").password("1234").regNo("860824-1655068").build();

        when(userRepository.findByUserId(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(SzsUser.class))).thenReturn(szsUser);
        when(passwordEncoder.encode(anyString())).thenReturn("test_encoded_password");

        //when
        SzsUserDto szsUserDto = userService.createUser(command);

        //then
        assertEquals(szsUser.getUserId(), szsUserDto.getUserId());
        assertEquals(szsUser.getName(), szsUserDto.getName());
        assertEquals(szsUser.getPassword(), szsUserDto.getPassword());
    }

    @Test
    @DisplayName("[Service] 회원 정보 조회 테스트")
    void getUsersTest() {
        //given
        String userId = "hong";
        SzsUser szsUser = SzsUser.builder()
                                 .id("test")
                                 .name("홍길동")
                                 .userId("hong")
                                 .password("1234")
                                 .regNo("860824-1655068")
                                 .build();

        when(jwtProvider.getUserInfo(anyString())).thenReturn(userId);
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(szsUser));
        
        //when
        SzsUserDto szsUserDto = userService.getUsers(request);

        //then
        assertNotNull(szsUserDto);
        assertEquals(userId, szsUserDto.getUserId());
    }

    @Test
    @DisplayName("[Service] 회원 로그인 테스트")
    public void loginTest(){

        //given
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        SzsUser szsUser = SzsUser.builder()
                                 .id("test")
                                 .name("홍길동")
                                 .userId("hong")
                                 .password("1234")
                                 .regNo("860824-1655068")
                                 .build();

        SzsUserDto szsUserDto = SzsUserDto.builder()
                                            .userId("hong")
                                            .password("1234")
                                            .build();
     
        when(userRepository.findByUserIdAndPassword(anyString(), anyString())).thenReturn(Optional.of(szsUser));
        when(jwtProvider.createToken(anyString())).thenReturn(accessToken);
        when(jwtProvider.createRefreshToken(anyString())).thenReturn(refreshToken);
        
        //when
        SzsUserTokenDto szsUserTokenDto = userService.login(szsUserDto);

        //then
        assertNotNull(szsUserTokenDto);
        assertEquals(szsUser.getUserId(), szsUserTokenDto.getUserId());
        assertEquals(accessToken, szsUserTokenDto.getAccessToken());
        assertEquals(refreshToken, szsUserTokenDto.getRefreshToken());
    }

    @Test
    @DisplayName("[Service] 회원 스크랩 정보 조회 테스트")
    public void testGetScrap() {

        //given
        String regNo = "860824-1655068";
        SzsUser szsUser = SzsUser.builder()
                                 .id("test")
                                 .name("홍길동")
                                 .userId("hong")
                                 .password("1234")
                                 .regNo(regNo)
                                 .build();

                    
        when(userRepository.findByRegNo(regNo)).thenReturn(Optional.of(szsUser));

        SzsUserReadCommand szsUserReadCommand = new SzsUserReadCommand();
        szsUserReadCommand.setRegNo("860824-1655068");

        //when
        SzsUserScrapDto result = userService.getScrap(szsUserReadCommand);

        //then
        assertNotNull(result.getData());
        assertNotNull(result.getData().getJsonList());
    }

    @Test
    @DisplayName("[Service] 세금 환급 조회 테스트")
    void refundTest(){

        //given
        String userId = "hong";
        SzsUser szsUser = SzsUser.builder()
                                 .id("test")
                                 .name("홍길동")
                                 .userId("hong")
                                 .password("1234")
                                 .regNo("860824-1655068")
                                 .build();

        Salary salary1 = Salary.builder().totalPayment("1000000").build();
        Salary salary2 = Salary.builder().totalPayment("2000000").build();
        List<Salary> salaries = Arrays.asList(salary1, salary2);

        TaxAmount taxAmount = TaxAmount.builder().calculatedTaxAmount("300000").build();

        Deduction deduction1 = Deduction.builder().gubun("퇴직연금").price("50000").build();
        Deduction deduction2 = Deduction.builder().gubun("보험료").price("120000").build();
        Deduction deduction3 = Deduction.builder().gubun("의료비").price("50000").build();
        Deduction deduction4 = Deduction.builder().gubun("교육비").price("30000").build();
        Deduction deduction5 = Deduction.builder().gubun("기부금").price("20000").build();
        
        List<Deduction> deductions = Arrays.asList(deduction1, deduction2, deduction3, deduction4, deduction5);

        when(jwtProvider.getUserInfo(anyString())).thenReturn(userId);
        when(salaryRepository.findByRegNo(szsUser.getRegNo())).thenReturn(salaries);
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(szsUser));
        when(taxAmountRepository.findBySzsUser(szsUser)).thenReturn(java.util.Optional.of(taxAmount));
        when(deductionRepository.findBySzsUser(szsUser)).thenReturn(deductions);

        //when
        RefundDto refundDto = userService.getRefund(request);

        //then
        assertNotNull(refundDto);
        assertEquals("0", refundDto.get결정세액());
        assertEquals("7500", refundDto.get퇴직연금세액공제());
        assertEquals("홍길동", refundDto.get이름());
    }
}
