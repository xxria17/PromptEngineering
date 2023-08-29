package com.dhxxn17.helpyou.data.model

data class ChatResponse(
    val intents: List<IntentData>,
    val text: String,
    val traits: TraitsData
)

data class IntentData(
    val confidence: Double,
    val id: Long,
    val name: String
)

data class TraitsData(
    val greeting: List<GreetingData>
)

data class GreetingData(
    val confidence: Double,
    val id: Long,
    val value: String
)