package com.edgar.kafka.alarm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

/**
 * Created by edgar on 14-10-22.
 */
public class AlarmEncoder implements Encoder<Alarm> {

    public AlarmEncoder(VerifiableProperties verifiableProperties) {
        /* This constructor must be present for successful compile. */
    }

    @Override
    public byte[] toBytes(Alarm alarm) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(alarm);
        } catch (JsonProcessingException e) {
            System.err.println(String.format("Json processing failed for object: %s", alarm.getClass().getName()));
            e.printStackTrace();
        }
        return "".getBytes();
    }
}
