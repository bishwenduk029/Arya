package com.arya.server.brain

import com.arya.compiler.annotations.Answer
import com.arya.compiler.domain.IntentList
import com.lordcodes.turtle.shellRun
import jakarta.inject.Singleton
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

@Singleton
class IntentExecutionContext {

    private val intentContext: IntentList = Json.decodeFromString(
        File(
            javaClass.classLoader.getResource("metadata/intents.json")?.path ?: "metadata/intents.json"
        ).readText()
    )

    fun execute(intent: String): String? {
        val intentModel = intentContext.intentModels.find {
            it.intent == intent
        }
        if (intentModel != null) {
            val clazz = Class.forName(intentModel.intentClass, true, IntentExecutionContext::class.java.classLoader)
            val ans =
                clazz.getMethod(intentModel.intentMethod, String::class.java).getAnnotation(Answer::class.java).response
            shellRun("./src/main/resources/coquiai_tts/tts.sh", listOf("--text", ans))
            shellRun("open", listOf("tts_output.wav"))
            return ans
            //return clazz.getMethod(intentModel.intentMethod, String::class.java).invoke(clazz.getConstructor().newInstance(), intentModel.examples[0])
        }
        return null
    }

}