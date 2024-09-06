package com.example.windowsPos2.global.websocketHandler

import com.example.windowsPos2.orderManagement.dto.OrderManagementDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder

//웹소켓 메시지를 처리하는 클래스
@Component
class WebSocketMessageHandler : TextWebSocketHandler() {
//    사용자 id와 웹소켓 세션을 매핑하는 hashmap, 사용자와 연결된 세션 추적
    private val sessionMap = HashMap<String, WebSocketSession>()

//    연결이 성립되었을 때 호출하는 메서드
    override fun afterConnectionEstablished(session : WebSocketSession) {
//        웹소켓 세션으로부터 사용자 id를 찾아냄
        val userId = searchUserName(session)
//        세션맵에 사용자 id와 웹소켓 세션 추가
        sessionMap[userId] = session
    }

//    연결이 닫혔을 때 호출되는 메서드
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
//        세션에서 사용자 id 호출
        val userId = searchUserName(session)
//        세션맵에서 사용자 id 제거
        sessionMap.remove(userId)
    }

//    웹소켓 세션의 URI를 파싱하여 사용자 id를 추출하는 메서드
    private fun searchUserName(session: WebSocketSession): String {
//        URI 파싱
        val uriComponent = UriComponentsBuilder.fromUriString(session.uri.toString()).build()
//    쿼리 매개변수에 udi값을 추출, 웹소켓에 연결할 때 "uid" 파라미터를 붙여서 보내야 searchUserName 메소드가 작동
        return uriComponent.queryParams.getFirst("uid") ?: ""
    }

//    주문 상태를 브로드캐스트하는 메서드
    fun broadcastOrderUpdate(orderManagementDto: OrderManagementDto) {
        val orderUpdateMessage = converToJson(orderManagementDto)
        println("잘 될거야 : $orderUpdateMessage")
        for (session in sessionMap.values) {
            try {
//            세션을 통해 메세지 전송
                println("real루 잘 보내질거야 : ${session.id}")
                session.sendMessage(TextMessage(orderUpdateMessage))
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

//    DTO 객체를 JSON 문자열로 변환하는 메서드
    private fun converToJson (orderManagementDto : OrderManagementDto) : String {
//        실제 구현에서는 Jackson 또는 Gson 등을 사용하여 JSON 변환
        return try {
            val objectMapper = ObjectMapper()
            objectMapper.registerModule(JavaTimeModule())
            objectMapper.writeValueAsString(orderManagementDto)
        } catch (e : Exception) {
            e.printStackTrace()
            "{}"
        }
    }
}