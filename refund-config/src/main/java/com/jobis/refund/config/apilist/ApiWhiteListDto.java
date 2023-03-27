package com.jobis.refund.config.apilist;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "api")
public class ApiWhiteListDto {
    private List<String> infos;
}