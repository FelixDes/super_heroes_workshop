package com.example.statistics.kafka.config;

import com.example.statistics.data.Fight;
import com.example.statistics.data.ScoreListDTO;
import com.example.statistics.data.serialization.FightDeserializer;
import com.example.statistics.data.serialization.ScoreListDTOSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaListenerFightToScoresDTOConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String offset;
    @Value("${groups.fight_winner_stats_group}")
    private String consumerFightWinnerStatsGroup;

    @Bean
    public ConsumerFactory<String, Fight> kafkaConsumerFactoryFightWinnerStats() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, FightDeserializer.class);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offset);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerFightWinnerStatsGroup);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ProducerFactory<String, ScoreListDTO> producerFactoryScoreDTO() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ScoreListDTOSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, ScoreListDTO> kafkaTemplateScoreDTO() {
        return new KafkaTemplate<>(producerFactoryScoreDTO());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Fight> kafkaListenerContainerFactoryFightToScoreDTO() {
        ConcurrentKafkaListenerContainerFactory<String, Fight> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerFactoryFightWinnerStats());
        factory.setReplyTemplate(kafkaTemplateScoreDTO());
        return factory;
    }
}
