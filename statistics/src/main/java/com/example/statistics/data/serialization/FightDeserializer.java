package com.example.statistics.data.serialization;

import com.example.statistics.data.Fight;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class FightDeserializer implements Deserializer<Fight> {
    private final ObjectMapper objectMapper;

    public FightDeserializer() {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JavaTimeModule());
    }
    @Override
    public Fight deserialize(String topic, byte[] data) {
        try {
            String s = new String(data, "UTF-8");
            return objectMapper.readValue(s, Fight.class);
        } catch (IOException e) {
            throw new SerializationException("Error during deserialization from byte[] to Fight");
        }
    }
}
