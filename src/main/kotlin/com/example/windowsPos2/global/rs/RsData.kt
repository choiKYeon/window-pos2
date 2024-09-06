package com.example.windowsPos2.global.rs

import com.fasterxml.jackson.annotation.JsonIgnore

data class RsData<T> (
    var resultCode : String = "",
    var msg : String = "",
    var data : T? = null
) {
    companion object {
        fun <T> of(resultCode : String, msg : String, data : T? = null): RsData<T> {
            return RsData(resultCode, msg, data)
        }
    }

    @JsonIgnore
    fun isSuccess(): Boolean {
        return resultCode.startsWith("S-")
    }

    @JsonIgnore
    fun isFail(): Boolean {
        return !isSuccess()
    }
}