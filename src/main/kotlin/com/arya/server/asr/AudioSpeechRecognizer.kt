package com.arya.server.asr

import com.arya.server.speechtotext.ISpeechToText
import io.micronaut.websocket.WebSocketSession
import jakarta.inject.Singleton
import mu.KotlinLogging
import rasa.client.RasaService
import rasa.domain.QuestionModel
import ws.schild.jave.Encoder
import ws.schild.jave.MultimediaObject
import ws.schild.jave.encode.AudioAttributes
import ws.schild.jave.encode.EncodingAttributes
import java.io.*

private val logger = KotlinLogging.logger(AudioSpeechRecognizer::class.java.canonicalName)

@Singleton
class AudioSpeechRecognizer(private val speechToTextTransformer: ISpeechToText, private val nluService: RasaService) {

    fun recognizeIntent(audio: ByteArray, session: WebSocketSession): String {
        logger.info { "Initiating Speech Recognition" }
        val wavFile = convertToWavAudioFile(audio)

        val userQuestion = speechToTextTransformer.parse(wavFile)

        logger.info {"Forwarding the question -> $userQuestion to client"}
        session.sendSync(userQuestion)

        return nluService.predict(QuestionModel(text = userQuestion)).intent.name
    }

    private fun convertToWavAudioFile(audio: ByteArray): File {
        logger.info { "Converting the WebM to Wav audio file" }
        val source = File("audio.webm")
        FileOutputStream(source).write(audio)
        val target = File("audio.wav")
        val sourceAudioAttributes = AudioAttributes().setChannels(1).setSamplingRate(16000).setCodec("pcm_s16le")
        val targetEncodingAttributes = EncodingAttributes()
        targetEncodingAttributes.setOutputFormat("wav")
        targetEncodingAttributes.setAudioAttributes(sourceAudioAttributes)
        val encoder = Encoder()
        encoder.encode(MultimediaObject(source), target, targetEncodingAttributes)
        source.delete()
        logger.info { "Successfully converted WebM to Wav audio file" }
        return target
    }
}