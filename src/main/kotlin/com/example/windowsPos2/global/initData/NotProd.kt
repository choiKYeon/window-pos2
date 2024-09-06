package com.example.windowsPos2.global.initData

import com.example.windowsPos2.member.entity.Member
import com.example.windowsPos2.member.repository.MemberRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

@Configuration
@Profile("dev")
class NotProd {

    @Bean
    fun initData(passwordEncoder: PasswordEncoder, memberRepository: MemberRepository) : CommandLineRunner {
        return CommandLineRunner {
            val member = memberRepository.findByUsername("gysoft")
            if (member == null) {
                val admin = Member.builder()
                    .name("관리자")
                    .username("gysoft")
                    .password(passwordEncoder.encode("1234"))
                    .createDate(LocalDateTime.now())
                    .build()

                memberRepository.save(admin)
            }
        }
    }
}