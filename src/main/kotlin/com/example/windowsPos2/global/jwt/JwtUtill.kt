package com.example.windowsPos2.global.jwt

import com.example.windowsPos2.global.config.SecurityUser
import com.example.windowsPos2.global.encrypt.EncryptionUtils
import com.example.windowsPos2.global.util.Util
import com.example.windowsPos2.member.dto.MemberDto
import com.example.windowsPos2.member.service.MemberService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Lazy
@Component
class JwtUtill (
    private val encryptionUtils: EncryptionUtils,
    private val memberService: MemberService,
    private val jwtProvider: JwtProvider
) {

//    토큰 발급하는 구문
    fun genAccessToken(usernameId: String): String? {
        val member = memberService.findByUsername(usernameId).orElse(null)
        val memberDto = MemberDto().apply {
            id = member.id
            username = member.username
            name = member.name
            authorities = member.authorities
        }
        return jwtProvider.genToken(memberDto.toClaims(), 60 * 60)
    }

    fun genRefreshToken(usernameId: String): String? {
        val member = memberService.findByUsername(usernameId).orElse(null)
        val memberDto = MemberDto().apply {
            id = member.id
            username = member.username
            name = member.name
            authorities = member.authorities
        }
        return jwtProvider.genToken(memberDto.toClaims(), 60 * 60 * 24 * 365 * 10)
    }

//    토큰으로 사용자 정보를 추출해서 SecurityUser객체를 생성하는 구문
    fun getUserFromAccessToken(accessToken: String): SecurityUser {
        val payloadBody = getDataFrom(accessToken)
        val id = (payloadBody["id"] as Int).toLong()
        val username = payloadBody["username"] as String
        val authoritiesList = payloadBody["authorities"] as List<Map<String, String>>

        val authorities = authoritiesList.map { authMap ->
            SimpleGrantedAuthority(authMap["authority"])
        }

        return SecurityUser(
            id,
            username,
            "",
            authorities
        )
    }

//    jwt 토큰의 페이로드에서 필요한 데이터 추출하는 구문
    fun getDataFrom(token : String): Map<String, Any?> {
        val payload: Claims = Jwts.parser()
            .setSigningKey(encryptionUtils.getSecretKey())
            .build()
            .parseClaimsJws(token)
            .body

        val bodyJson = payload["body"] as String
        val bodyClaims = Util.toMap(bodyJson) as Map<String, Any?>

        return mapOf(
            "id" to bodyClaims["id"],
            "name" to bodyClaims["name"],
            "username" to bodyClaims["username"],
            "authorities" to bodyClaims["authorities"]
        )
    }
}