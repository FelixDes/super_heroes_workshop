package com.example.statistics.data.serialization;

import com.example.statistics.data.ScoreListDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class ScoreListDTOSerializer implements Serializer<ScoreListDTO> {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, ScoreListDTO data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error during serialization from ScoreListDTO to byte[]", e);
        }
    }
}
