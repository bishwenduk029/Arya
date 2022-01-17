package com.arya.compiler.domain

import kotlinx.serialization.Serializable

@Serializable
data class IntentModel(val intent: String, val examples: List<String>, val intentClass: String, val intentMethod: String)

@Serializable
data class IntentList(val intentModels: List<IntentModel>)