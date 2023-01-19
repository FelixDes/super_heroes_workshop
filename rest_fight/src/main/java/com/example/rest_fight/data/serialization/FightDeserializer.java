package com.example.rest_fight.data.serialization;

import com.example.rest_fight.data.Fight;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

@RequiredArgsConstructor
public class FightDeserializer implements Deserializer<Fight> {
    private final ObjectMapper objectMapper;

    @Override
    public Fight deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, Fight.class);
        } catch (IOException e) {
            throw new SerializationException("Error during deserialization from byte[] to Fight");
        }
    }
}
