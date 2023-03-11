package com.project.services.statistics.data.serialization;

import com.project.services.statistics.data.ScoreListDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class ScoreListDTODeserializer implements Deserializer<ScoreListDTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ScoreListDTO deserialize(String topic, byte[] data) {
        try {
            String s = new String(data, "UTF-8");
            return objectMapper.readValue(s, ScoreListDTO.class);
        } catch (IOException e) {
            throw new SerializationException("Error during deserialization from byte[] to ScoreListDTO", e);
        }
    }
}
