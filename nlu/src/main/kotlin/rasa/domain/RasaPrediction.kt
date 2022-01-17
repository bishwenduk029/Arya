package rasa.domain

data class QuestionModel(val text: String)

data class PredictedIntent(val name: String)

data class RasaPrediction(val intent: PredictedIntent)