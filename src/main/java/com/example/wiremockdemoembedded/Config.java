package com.example.wiremockdemoembedded;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                ;
    }
}
