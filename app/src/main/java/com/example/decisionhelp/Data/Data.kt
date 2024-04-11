package com.example.decisionhelp.Data

data class SignupRequest(
    val id: String,
    val password: String
)

data class IdCheckRequest(val id: String)