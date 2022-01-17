package com.arya.compiler.processors

import api.ISerializer
import com.arya.compiler.annotations.Intent
import com.arya.compiler.annotations.Questions
import com.arya.compiler.domain.IntentList
import com.arya.compiler.domain.IntentModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import rasa.serializers.YamlGenerator
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement


@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes("com.arya.compiler.annotations.Intent")
open class IntentProcessor : AbstractProcessor() {
    private val listOfIntentModels = mutableListOf<IntentModel>()
    private val serializer: ISerializer<Map<String, List<Map<String, String>>>> = YamlGenerator()

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        val intentAnnotatedMethods = roundEnv?.getElementsAnnotatedWith(Intent::class.java)
        if (intentAnnotatedMethods?.size == 0) {
            val intentMap = mapOf("nlu" to listOfIntentModels.map {
                mapOf(
                    "intent" to it.intent,
                    "examples" to it.examples.joinToString("\n") { question ->
                        "- $question"
                    }
                )
            })
            try {
                serializer.serialize(intentMap)
                val metadata = File("src/main/resources/metadata/intents.json")
                metadata.writeText(Json.encodeToString(IntentList(listOfIntentModels)))
            } catch (ex: Exception){
                ex.printStackTrace()
                return false
            }

            return true
        }

        intentAnnotatedMethods?.forEach {
            if (it.kind != ElementKind.METHOD) {
                println("@Intent annotation can only be applied to a method/function. Please check and rectify. Current applied on ${it.simpleName}")
                return false
            }
            val methodName = it.simpleName
            var enclosing = it.enclosingElement
            while (enclosing.kind !== ElementKind.PACKAGE) {
                enclosing = enclosing.enclosingElement
            }
            val packageElement: PackageElement = enclosing as PackageElement
            val className = "${packageElement.qualifiedName}.${it.enclosingElement.simpleName}"
            val intentName = it.getAnnotation(Intent::class.java).intentName
            val questions = it.getAnnotation(Questions::class.java).questions
            val intentModel = IntentModel(intentName, questions.toCollection(mutableListOf()), className, methodName.toString())
            listOfIntentModels.add(intentModel)
        }
        return true
    }
}