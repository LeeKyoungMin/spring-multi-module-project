package com.jobis.refund.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jobis.refund.config.exception.RefundException;
import com.jobis.refund.config.exception.StatusEnum;
import com.jobis.refund.domain.User.entity.SzsUser;
import com.jobis.refund.repository.user.SzsUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

    private final SzsUserRepository szsUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        SzsUser szsUser = szsUserRepository.findByUserId(userId)
                                            .orElseThrow(() -> new RefundException(StatusEnum.USER_NOT_FOUND, StatusEnum.USER_NOT_FOUND.getDescription()));
    
        return new UserPrincipalDetails(szsUser);
    }
}
