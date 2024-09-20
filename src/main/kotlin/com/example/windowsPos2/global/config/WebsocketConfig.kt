package com.example.windowsPos2.global.config

import com.example.windowsPos2.global.handshakeInterceptor.JwtHandshakeInterceptor
import com.example.windowsPos2.global.websocketHandler.WebSocketMessageHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry


@EnableWebSocket
@Configuration
class WebsocketConfig(
    private val webSocketMessageHandler: WebSocketMessageHandler,
    private val jwtHandshakeInterceptor: JwtHandshakeInterceptor
): WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        // /orders 엔드포인트에 핸들러로 등록 후 CORS 요청 허용
        registry.addHandler(webSocketMessageHandler, "/ws/orders")
            .setAllowedOrigins("*")
            .addInterceptors(jwtHandshakeInterceptor)
//            .withSockJS()
//            .setInterceptors(jwtHandshakeInterceptor)
    }
}