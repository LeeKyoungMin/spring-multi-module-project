package com.jobis.refund.config.apilist;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApiWhiteList implements InitializingBean{

    private final static List<String> whiteList = Collections.synchronizedList(new LinkedList<>());

    private final ApiWhiteListDto apiWhiteListDto;

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        for(String api : apiWhiteListDto.getInfos()){
            whiteList.add(api);
        }
    }
    
    public static String[] getApiWhList(){
        String[] apiArray = whiteList.toArray(new String[0]);
        return apiArray;
    }
}
