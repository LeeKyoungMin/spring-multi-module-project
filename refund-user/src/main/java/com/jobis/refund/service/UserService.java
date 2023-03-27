package com.jobis.refund.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jobis.refund.config.exception.RefundException;
import com.jobis.refund.config.exception.StatusEnum;
import com.jobis.refund.config.userlist.SzsUserList;
import com.jobis.refund.domain.User.dto.Errors;
import com.jobis.refund.domain.User.dto.JsonList;
import com.jobis.refund.domain.User.dto.RefundDto;
import com.jobis.refund.domain.User.dto.SzsUserCommand;
import com.jobis.refund.domain.User.dto.SzsUserDto;
import com.jobis.refund.domain.User.dto.SzsUserReadCommand;
import com.jobis.refund.domain.User.dto.SzsUserScrapDto;
import com.jobis.refund.domain.User.dto.SzsUserTokenDto;
import com.jobis.refund.domain.User.dto.Value;
import com.jobis.refund.domain.User.entity.Deduction;
import com.jobis.refund.domain.User.entity.Salary;
import com.jobis.refund.domain.User.entity.SzsUser;
import com.jobis.refund.domain.User.entity.TaxAmount;
import com.jobis.refund.repository.deduction.DeductionRepository;
import com.jobis.refund.repository.salary.SalaryRepository;
import com.jobis.refund.repository.taxAmount.TaxAmountRepository;
import com.jobis.refund.repository.user.SzsUserRepository;
import com.jobis.refund.security.jwt.JwtProvider;

import ch.qos.logback.core.status.Status;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final SzsUserRepository szsUserRepository;

    private final SalaryRepository salaryRepository;

    private final TaxAmountRepository taxAmountRepository;

    private final DeductionRepository deductionRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    public SzsUserDto getUsers(HttpServletRequest request) {

        SzsUser szsUser = getUserInfo(request);

        SzsUserDto szsUserDto = szsUser.toDto();
        return szsUserDto;
    }

    public SzsUserDto createUser(SzsUserCommand command) {

        if(!SzsUserList.getNames().contains(command.getName()) || !SzsUserList.getRegNos().contains(command.getRegNo())){
            throw new RefundException(StatusEnum.BAD_REQUEST, StatusEnum.BAD_REQUEST.getDescription());
        }

        Optional<SzsUser> existedUser = szsUserRepository.findByUserId(command.getUserId());
        if(existedUser.isPresent()){
            throw new RefundException(StatusEnum.USERID_ALREAD_EXIST, StatusEnum.USERID_ALREAD_EXIST.getDescription());
        }

        SzsUser szsUser = SzsUser.builder()
                                 .userId(command.getUserId())
                                 .name(command.getName())
                                 .password(passwordEncoder.encode(command.getPassword()))
                                 .regNo(passwordEncoder.encode(command.getRegNo()))
                                 .build();

        SzsUser user = szsUserRepository.save(szsUser);

        SzsUserDto szsUserDto = user.toDto();

        return szsUserDto;
    }

    public SzsUserTokenDto login(SzsUserDto szsUserDto) {

        if(!StringUtils.hasText(szsUserDto.getUserId()) || !StringUtils.hasText(szsUserDto.getPassword())){
            throw new RefundException(StatusEnum.USER_LOGIN_FAIL, StatusEnum.USER_LOGIN_FAIL.getDescription());
        }

        SzsUser existedUser = szsUserRepository.findByUserIdAndPassword(szsUserDto.getUserId(), szsUserDto.getPassword())
                                                .orElseThrow(() -> new RefundException(StatusEnum.USER_NOT_FOUND, StatusEnum.USER_NOT_FOUND.getDescription()));

        String accessToken = jwtProvider.createToken(szsUserDto.getUserId());
        String refreshToken = jwtProvider.createRefreshToken(szsUserDto.getUserId());

        SzsUserDto currentUser = existedUser.toDto();

        SzsUserTokenDto szsUserTokenDto = SzsUserTokenDto.builder()
                                                         .userId(currentUser.getUserId())
                                                         .name(currentUser.getName())
                                                         .accessToken(accessToken)
                                                         .refreshToken(refreshToken)
                                                         .build();
        return szsUserTokenDto;    
    }

	public SzsUserScrapDto getScrap(@Valid SzsUserReadCommand szsUserReadCommand) {

        SzsUserScrapDto szsUserScrapDto = new SzsUserScrapDto();
        Value dataDto = new Value();
        JsonList jsonList = new JsonList();
        List<StatusEnum> status = new ArrayList<>();

        SzsUser szsUser = szsUserRepository.findByRegNo(szsUserReadCommand.getRegNo())
                                            .orElseThrow(() -> new RefundException(StatusEnum.USER_NOT_FOUND, StatusEnum.USER_NOT_FOUND.getDescription()));


        List<Salary> salarys = salaryRepository.findByRegNo(szsUserReadCommand.getRegNo());
        if(salarys.isEmpty()){
            szsUserScrapDto.setStatus("fail");
            status.add(StatusEnum.SALARY_NOT_FOUND);
        }else{
            szsUserScrapDto.setStatus("success");
            jsonList.set급여(salarys.stream().map(Salary::toDto).collect(Collectors.toList()));
            
            szsUserScrapDto.setErrors(null);
        }

        Optional<TaxAmount> taxAmount = taxAmountRepository.findBySzsUser(szsUser);
        if(!taxAmount.isPresent()){
            szsUserScrapDto.setStatus("fail");
            status.add(StatusEnum.TAXAMOUNT_NOT_FOUND);
        }else{
            szsUserScrapDto.setStatus("success");
            jsonList.set산출세액(TaxAmount.toDto(taxAmount.get()));

            szsUserScrapDto.setErrors(null);
        }

        List<Deduction> deduction = deductionRepository.findBySzsUser(szsUser);
        if(deduction.isEmpty()){
            szsUserScrapDto.setStatus("fail");
            status.add(StatusEnum.DEDUCTION_NOT_FOUND);
        }else{
            szsUserScrapDto.setStatus("success");
            jsonList.set소득공제(deduction.stream().map(Deduction::toDto).collect(Collectors.toList()));
            
            szsUserScrapDto.setErrors(null);
        }

        dataDto.setAppVer("202111110312");
        dataDto.setErrMsg("");
        dataDto.setCompany("삼쩜삼");
        dataDto.setSvcCd("test01");
        dataDto.setHostNm("jobis-codetest");
        dataDto.setWorkerReqDt(String.valueOf(LocalDateTime.now()));
        dataDto.setWorkerResDt(String.valueOf(LocalDateTime.now()));
        dataDto.setJsonList(jsonList);

        szsUserScrapDto.setData(dataDto);
        
        List<Errors> errors = new ArrayList<>();
        for(StatusEnum statusEnum : status){
            Errors error = new Errors();
            error.setCode(statusEnum.getCode());
            error.setMessage(statusEnum.getDescription());
            errors.add(error);
        }

        szsUserScrapDto.setErrors(errors);

		return szsUserScrapDto;
	}

    public RefundDto getRefund(HttpServletRequest request) {

        SzsUser szsUser = getUserInfo(request);
        RefundDto refundDto = new RefundDto();
        List<Salary> salaries = salaryRepository.findByRegNo(szsUser.getRegNo());

        //총급여
        long sumSalary = salaries.stream()
                                 .mapToLong(salary -> Long.parseLong(salary.getTotalPayment()))
                                 .sum();

        //산출세액
        long calculatedTaxAmount = 0L;
        Optional<TaxAmount> taxAmount = taxAmountRepository.findBySzsUser(szsUser);

        if(!taxAmount.isPresent()){
            calculatedTaxAmount = 0L;
        }else{
            calculatedTaxAmount = Long.parseLong(taxAmount.get().getCalculatedTaxAmount());
        }

        //근로소득세액공제금액 = 산출세액 * 0.55
        //퇴직연금세액공제금액 = 퇴직연금 납입액 * 0.15
        //특별세액공제금액 = [보험료납입금액  * 12%] + [(의료비납앱금액 - 총급여 * 3%) * 15%] 단, 0미만이면 0 + [교육비납입금액 * 15%] + [기부금납입금액 * 15%]  
        //표준세액공제금액 = 특별세액공제금액 합 < 130,000이면 130,000 / 합 >= 130,000이면 0
        //결정세액 < 0 일 경우 0으로 처리

        //근로소득공제금액
        long gunloAmount = Math.round(calculatedTaxAmount * 0.55);

        List<Deduction> deductions = deductionRepository.findBySzsUser(szsUser);

        Map<String, Double> totalPricesByGubun = deductions.stream()
                                                .collect(Collectors.groupingBy(Deduction::getGubun, Collectors.summingDouble(d -> Double.parseDouble(d.getPrice()))));


        //퇴직연금 납입액 및 퇴직연금세액공제금액
        long totalToijikAmount = Math.round(totalPricesByGubun.get("퇴직연금") * 0.15);

        //보험료납입금액
        long totalBohumAmount = Math.round(totalPricesByGubun.get("보험료") * 0.12);

        //의료비납입금액
        long medicalAmount = Math.round(Math.round(totalPricesByGubun.get("의료비") - Math.round(sumSalary * 0.03)) * 0.15);
        long totalMedicalAmount = medicalAmount < 0 ? 0 : medicalAmount;

        //교육비납입금액
        long totalEduAmount = Math.round(totalPricesByGubun.get("교육비") * 0.15);

        //기부금납입금액
        long totalGibuAmount = Math.round(totalPricesByGubun.get("기부금") * 0.15);
        
        //특별세액공제금액
        long specialTax = totalBohumAmount + totalMedicalAmount + totalEduAmount + totalGibuAmount;

        //표준세액공제금액
        long standardTax = specialTax < 130000 ? 130000 : 0;

        //결정세액 = 산출세액 - 근로소득세액공제금액 - 특별세액공제금액 - 표준세액공제금액 - 퇴직연금세액공제금액
        long finalTax = calculatedTaxAmount - gunloAmount - specialTax - standardTax - totalToijikAmount;
        long finalTaxAmount = finalTax < 0 ? 0 : finalTax;

        refundDto.set결정세액(String.valueOf(finalTaxAmount));
        refundDto.set퇴직연금세액공제(String.valueOf(totalToijikAmount));
        refundDto.set이름(szsUser.getName());

        return refundDto;
    }
    
    private SzsUser getUserInfo(HttpServletRequest request){
        String accessToken = request.getHeader("Authorization");
        String userId = jwtProvider.getUserInfo(accessToken);

        SzsUser szsUser = szsUserRepository.findByUserId(userId)
                                            .orElseThrow(() -> new RefundException(StatusEnum.USER_NOT_FOUND, StatusEnum.USER_NOT_FOUND.getDescription()));
        return szsUser;
    }
}
