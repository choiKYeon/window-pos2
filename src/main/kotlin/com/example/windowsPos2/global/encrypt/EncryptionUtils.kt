package com.example.windowsPos2.global.encrypt


import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Component
class EncryptionUtils {

    @Value("\${custom.jwt.secretKey}")
    private lateinit var base64SecretString: String
    private lateinit var secretKey: SecretKey

    @PostConstruct
    fun init() {
//        HS512에 적합한 키 길이 확인 및 변환
        val decodeKey = java.util.Base64.getDecoder().decode(base64SecretString)
//        JWT 생성에 사용될 SecretKey 생성
        this.secretKey = Keys.hmacShaKeyFor(decodeKey)
    }

    fun getSecretKey(): SecretKey {
        return secretKey
    }
}