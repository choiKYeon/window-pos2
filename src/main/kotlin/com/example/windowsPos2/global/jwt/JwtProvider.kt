package com.example.windowsPos2.global.jwt

import com.example.windowsPos2.global.encrypt.EncryptionUtils
import com.example.windowsPos2.global.util.Util
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm

import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtProvider (
    private val encryptionUtils: EncryptionUtils
) {
    private var cachedSecretKey: SecretKey? = null

    private fun _getSecretKey(): SecretKey {
        return encryptionUtils.getSecretKey()
    }

    fun getSecretKey(): SecretKey {
        if (cachedSecretKey == null) cachedSecretKey = _getSecretKey()

        return cachedSecretKey ?: throw IllegalStateException("SecretKey is null")
    }

    fun getUsername(token: String): String? {
        val usernameObject = getClaims(token)?.get("username")
        return usernameObject?.toString()
    }

    fun genToken(claims: Map<String, Any?>, seconds: Int): String {
        val now = Date().time
        val accessTokenExpiresIn = Date(now + 1000L * seconds)

        return Jwts.builder()
            .claim("body", Util.json.toStr(claims))
            .setExpiration(accessTokenExpiresIn)
            .signWith(getSecretKey(), SignatureAlgorithm.HS512)
            .compact()
    }

    fun verify(token : String): Boolean {
        try {
            Jwts.parser()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
        } catch (e: Exception) {
           return false
        }
        return true
    }

    fun getClaims(token : String): Map<String, Any>? {
        val body = Jwts.parser()
            .setSigningKey(getSecretKey())
            .build()
            .parseClaimsJws(token)
            .body
            .get("body", String::class.java)

        return Util.toMap(body)
    }
}