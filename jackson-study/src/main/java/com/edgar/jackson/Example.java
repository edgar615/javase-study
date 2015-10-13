package com.edgar.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by edgar on 15-10-13.
 */
public class Example {
    public static void main(String[] args) throws JsonProcessingException {
        AlarmUser alarmUser = new AlarmUser();
        alarmUser.setAlarmUserId("id");
        alarmUser.setAlarmUserName("name");
        alarmUser.getMap().put("username", "edgar");

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        System.out.println(mapper.writeValueAsString(alarmUser));
    }
}
