package com.edgar.kafka.alarm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.serializer.Decoder;
import kafka.utils.VerifiableProperties;

import java.io.IOException;

/**
 * Created by edgar on 14-10-22.
 */
public class AlarmDecoder implements Decoder<Alarm> {
    public AlarmDecoder(VerifiableProperties verifiableProperties) {
        /* This constructor must be present for successful compile. */
    }

    @Override
    public Alarm fromBytes(byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(bytes, Alarm.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
