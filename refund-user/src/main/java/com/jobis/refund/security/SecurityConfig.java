package com.jobis.refund.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import com.jobis.refund.config.apilist.ApiWhiteList;
import com.jobis.refund.security.jwt.JwtAuthenticationFilter;
import com.jobis.refund.security.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtProvider jwtProvider;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsServiceImpl);

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .cors().disable()
            .formLogin().disable()
            .authorizeRequests()
                .requestMatchers(request -> CorsUtils.isPreFlightRequest(request)).permitAll()
                .antMatchers(ApiWhiteList.getApiWhList()).permitAll()
                .anyRequest().authenticated()
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
            .httpBasic();
        http.headers().frameOptions().disable();
        return http.build();
    } 
}
