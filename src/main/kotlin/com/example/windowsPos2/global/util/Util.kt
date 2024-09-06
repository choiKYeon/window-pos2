package com.example.windowsPos2.global.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper

object Util {
    private val objectMapper = ObjectMapper()
    //        싱글톤인 이유는, ObjectMapper는 무겁고 상태를 가질 수 있는 객체이다. 여러 번 생성하면 성능에 영향을 미치기 때문에 싱글톤을 사용해야함. 즉 생성비용이 비쌈
    object json {
        fun toStr(map: Map<String, Any?>): String? {
            return try {
                objectMapper.writeValueAsString(map)
            } catch (e: JsonProcessingException) {
                null
            }
        }
    }
    //    생성 비용이 저렴하고, 상태를 유지할 필요가 없기에 싱글톤일 필요가 없음.
    fun toMap(jsonStr: String) : Map<String, Any>? {
        return try {
            objectMapper.readValue(jsonStr, LinkedHashMap::class.java) as Map<String, Any>
        } catch (e: JsonProcessingException) {
            null
        }
    }
}