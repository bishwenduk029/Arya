package com.arya.server.controllers

import com.arya.server.asr.AudioSpeechRecognizer
import com.arya.server.brain.IntentExecutionContext
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.OnClose
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen
import io.micronaut.websocket.annotation.ServerWebSocket
import jakarta.inject.Singleton
import org.reactivestreams.Publisher


@ServerWebSocket("/ws/chat/audio")
@Singleton
class Bot(private val audioSpeechRecognizer: AudioSpeechRecognizer, private val intentExecutionContext: IntentExecutionContext) {

    @OnOpen
    fun onOpen(session: WebSocketSession): Publisher<String> {
        return session.send("Hello")
    }

    @OnMessage
    fun onMessage(
        message: ByteArray,
        session: WebSocketSession
    ): Publisher<String>? {
        val intent = audioSpeechRecognizer.recognizeIntent(message, session)
        val answer = intentExecutionContext.execute(intent)
        return session.send(answer)
    }

    @OnClose
    fun onClose(
        session: WebSocketSession
    ): Publisher<String> {
        return session.send("msg")
    }
}