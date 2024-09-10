package com.example.windowsPos2.global.config

import com.example.windowsPos2.global.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val baseConfig: BaseConfig
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers("/**").permitAll() // 로그인은 누구나 가능
                    .anyRequest().authenticated() // 그 외에는 인증된 사용자만 가능
            }
            .cors { cors -> cors.configure(http)} // cors 활성화 , 브라우저 호환성을 높임
            .csrf { csrf -> csrf.disable() } // csrf 비활성화
            .httpBasic { httpBasic -> httpBasic.disable() } // httpBasic 로그인 방식 끄기 (jwt 사용)
            .formLogin { formLogin -> formLogin.disable() } // formLogin 로그인 방식 끄기 (jwt 사용)
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // 세션 비활성화 (jwt 사용)
            .addFilterBefore (
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration : AuthenticationConfiguration) : AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}