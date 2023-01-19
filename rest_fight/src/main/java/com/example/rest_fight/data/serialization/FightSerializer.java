package com.example.rest_fight.data.serialization;

import com.example.rest_fight.data.Fight;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.beans.factory.annotation.Autowired;

//@RequiredArgsConstructor
//@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FightSerializer implements Serializer<Fight> {
    private final ObjectMapper objectMapper;

    public FightSerializer(@Autowired ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(String topic, Fight data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
//            throw new SerializationException("Error during serialization from Fight to byte[]");
            throw new RuntimeException(e);
        }
    }
}
