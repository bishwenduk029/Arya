package com.arya.server.speechtotext.providers

import com.fasterxml.jackson.annotation.JsonProperty

data class RecognizerResult(@JsonProperty("text") val text: String)