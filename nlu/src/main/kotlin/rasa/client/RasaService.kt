package rasa.client

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import rasa.domain.QuestionModel
import rasa.domain.RasaPrediction

@Client("http://localhost:5005/")
interface RasaService {

    @Post("model/parse")
    fun predict(@Body question: QuestionModel): RasaPrediction
}