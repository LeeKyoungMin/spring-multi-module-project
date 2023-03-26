package com.jobis.refund.domain.User.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.jobis.refund.domain.User.dto.SzsUserDto;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class SzsUser {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String userId;

    private String password;

    private String name;

    private String regNo;

    @Builder
    public SzsUser(String id, String userId, String password, String name, String regNo){
        this.id = id;
        this.userId = Objects.requireNonNull(userId);
        this.password = Objects.requireNonNull(password);
        this.name = Objects.requireNonNull(name);
        this.regNo = Objects.requireNonNull(regNo);
    }

    public SzsUserDto toDto(){
        return new SzsUserDto(this.id, this.userId, this.password, this.name, this.regNo);
    }
}
