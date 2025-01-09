package com.example.ourchatapp.model

data class messages(
    val sender: String? = "",
    val receiver: String? = "",
    val messages: String? = "",
    val time: String? = ""
)

{
    val id : String get() = "$sender-$receiver-$messages-$time"
}