package com.example.windowsPos2.member.dto

import org.springframework.security.core.GrantedAuthority


data class MemberDto (
    var id: Long? = null,
    var name: String? = null,
    var username: String? = null,
    var password: String? = null,
    var authorities: Collection<GrantedAuthority>? = null) {

//    토큰 생성 정보
    fun toClaims() : Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "username" to username,
            "authorities" to authorities?.map { it.authority }
        )
    }
}