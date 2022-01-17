package com.arya.server.skills.greetings

import com.arya.compiler.annotations.Answer
import com.arya.compiler.annotations.Intent
import com.arya.compiler.annotations.IntentHandler
import com.arya.compiler.annotations.Questions
import io.micronaut.context.annotation.Executable

@IntentHandler
class GreetingsIntentHandler {

    @Intent("greet")
    @Questions(["What's up [Sara](name)?", "How are you [Sara](name)?", "How are you doing [Sara](name)?"])
    @Answer("I am fine, how are you doing, my love ")
    fun greetUser(question: String): String {
        return "Bishwendu"
    }

    @Intent("faq")
    @Questions(["What language do you speak?", "Do you speak with tongue?", "why do you talk so much?"])
    fun faq(question: String){

    }

}