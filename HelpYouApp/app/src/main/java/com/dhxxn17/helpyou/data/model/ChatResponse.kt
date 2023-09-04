package com.dhxxn17.helpyou.data.model

data class ChatResponse(
    val intents: List<IntentData>,
    val text: String,
    val traits: Map<String, List<DynamicData>>
)

data class IntentData(
    val confidence: Double,
    val id: Long,
    val name: String
)

data class DynamicData(
    val confidence: Double,
    val id: Long,
    val value: String
)