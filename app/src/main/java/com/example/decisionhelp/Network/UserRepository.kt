package com.example.decisionhelp.Network

import com.example.decisionhelp.Data.IdCheckRequest
import com.example.decisionhelp.Data.SignupRequest

class UserRepository(private val apiService: ApiService) {
    suspend fun signUp(signupRequest: SignupRequest) = apiService.signUp(signupRequest)

    suspend fun checkId(id: String): Boolean {
        val response = apiService.checkId(IdCheckRequest(id))
        return response.isSuccessful && response.body() == true
    }
}