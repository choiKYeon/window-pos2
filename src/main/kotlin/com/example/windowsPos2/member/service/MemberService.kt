package com.example.windowsPos2.member.service

import com.example.windowsPos2.member.entity.Member
import com.example.windowsPos2.member.repository.MemberRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class MemberService (
    private val memberRepository: MemberRepository
) {

    fun findByUsername(username: String): Optional<Member> {
        val member = memberRepository.findByUsername(username)
        return Optional.ofNullable(member)
    }
}