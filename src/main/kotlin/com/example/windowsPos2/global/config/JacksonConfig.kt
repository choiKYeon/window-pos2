package com.example.windowsPos2.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

//JSON 직렬화/역직렬화 과정에서 날짜 및 시간 관련 객체를 처리할 수 있게 해줌
@Configuration
class JacksonConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        return objectMapper
    }
}