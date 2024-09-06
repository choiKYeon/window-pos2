package com.example.windowsPos2.global.config

import com.example.windowsPos2.global.jwt.JwtProvider
import com.example.windowsPos2.global.jwtinterceptor.JwtInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

// MVC설정을 위한 클래스, JWT를 사용하여 인증 관련 로직을 인터셉터로 처리
@Configuration
class MvcConfig (
    private val jwtProvider : JwtProvider
) : WebMvcConfigurer {

//    CORS 설정하는 구문, 프론트엔드와 백엔드가 다른 도메인이여도 허용하도록 도와줌
    override fun addCorsMappings(corsRegistry : CorsRegistry) {
        corsRegistry.addMapping("/**") // 모든 경로에 CORS 규칙을 적용
            .allowedOrigins("http://localhost:8080") // 해당 주소에서 오는 요청을 허용. 즉, 프론트엔드가 이 도메인에서 요청을 보낼 수 있음
            .allowedMethods("*") // 모든 HTTP 메서드 허용 (POST, GET, PUT, DELETE 등)
            .allowedHeaders("*") // 모든 HTTP 헤더 허용
            .allowedMethods(true.toString()) // 쿠키, 인증 정보를 포함한 자격 증명을 허용
    }

//    JWT를 사용하여 인증 관련 로직을 인터셉터로 처리
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(JwtInterceptor(jwtProvider))
            .addPathPatterns("/**") // 모든 경로에 대해 인터셉터를 적용함
            .excludePathPatterns(
                "/api/v1/member/login"
            ) // 로그인 요청을 JWT 검증을 생략
    }
}