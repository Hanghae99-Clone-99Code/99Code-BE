package com.hanghae.code99.domain.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.code99.controller.request.RoomRequestDto;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class RoomRequestConverter implements Converter<String, RoomRequestDto> {

    private final ObjectMapper objectMapper;
    public RoomRequestConverter(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public RoomRequestDto convert (String value){
        return objectMapper.readValue(value, new TypeReference<>() {
        });
    }


}
