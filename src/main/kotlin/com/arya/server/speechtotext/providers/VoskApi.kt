package com.arya.server.speechtotext.providers

import com.arya.server.speechtotext.ITextToSpeech
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.inject.Singleton
import mu.KotlinLogging
import org.vosk.Model
import org.vosk.Recognizer
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream

private val logger = KotlinLogging.logger(VoskApi::class.java.canonicalName)

@Singleton
class VoskApi : ITextToSpeech {
    private val model = Model(javaClass.classLoader.getResource("vosk/vosk-model-small-en-in-0.4")?.path)

    override fun parse(wavFile: File): String {
        logger.info { "Parsing WAV file to text" }
        val audioInputStream = BufferedInputStream(FileInputStream(wavFile))
        val recognizer = Recognizer(model, 16000F)
        var nbytes: Int
        val b = ByteArray(4096)
        while (audioInputStream.read(b).also { nbytes = it } >= 0) {
            recognizer.acceptWaveForm(b, nbytes)
        }
        logger.info("Successfully parsed audio to text")
        audioInputStream.close()
        wavFile.delete()
        val result = recognizer.finalResult
        val objectMapper = ObjectMapper()
        return objectMapper.readValue(result, RecognizerResult::class.java).text
    }
}