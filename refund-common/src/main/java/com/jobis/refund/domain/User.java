package com.jobis.refund.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class User {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String userId;

    private String password;

    private String name;

    private String regNo;

    @Builder
    public User(String id, String userId, String password, String name, String regNo){
        this.id = Objects.requireNonNull(id);
        this.userId = Objects.requireNonNull(userId);
        this.password = Objects.requireNonNull(password);
        this.name = Objects.requireNonNull(name);
        this.regNo = Objects.requireNonNull(regNo);
    }
}
