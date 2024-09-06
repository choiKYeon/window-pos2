package com.example.windowsPos2.global.config

import com.example.windowsPos2.global.websocketHandler.WebSocketMessageHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebsocketConfig (
    private val webSocketMessageHandler : WebSocketMessageHandler
) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry : WebSocketHandlerRegistry) {
//        /ws/orders 엔드포인트에 핸들러로 등록 후 CORS요청을 허용
        registry.addHandler(webSocketMessageHandler, "/ws/orders").setAllowedOrigins("*")
    }
}