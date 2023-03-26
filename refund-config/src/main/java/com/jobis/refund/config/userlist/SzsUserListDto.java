package com.jobis.refund.config.userlist;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "szs")
public class SzsUserListDto {
    private List<String> names;

    private List<String> regNos;

}
