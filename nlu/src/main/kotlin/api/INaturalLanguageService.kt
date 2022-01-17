package api

import rasa.domain.QuestionModel

interface INaturalLanguageService<T> {
    fun predict(question: QuestionModel): T
}