package com.jobis.refund.config.userlist;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SzsUserList implements InitializingBean{

    private final static List<String> names = Collections.synchronizedList(new LinkedList<>());
    private final static List<String> regNos = Collections.synchronizedList(new LinkedList<>());

    private final SzsUserListDto szsUserListDto;

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        for(String name : szsUserListDto.getNames()){
            names.add(name);
        }

        for(String regNo : szsUserListDto.getRegNos()){
            regNos.add(regNo);
        }
    }
    
    public static List<String> getNames(){
        return names;
    }

    public static List<String> getRegNos(){
        return regNos;
    }
}
