package com.example.windowsPos2.member.repository

import com.example.windowsPos2.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long>{
    fun findByUsername(username : String): Member?

    fun findByUsernameAndPassword(username : String, password: String): Member?
}