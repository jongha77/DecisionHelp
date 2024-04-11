package com.example.decisionhelp.Network

import com.example.decisionhelp.Data.IdCheckRequest
import com.example.decisionhelp.Data.LoginRequest
import com.example.decisionhelp.Data.SignupRequest
import com.example.decisionhelp.Data.VoterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/signup")
    suspend fun signUp(@Body requestBody: SignupRequest): Response<Unit>

    @POST("/idcheck")
    suspend fun checkId(@Body request: IdCheckRequest): Response<Boolean>

    @POST("/login")
    suspend fun login(@Body request: LoginRequest): Response<Boolean>

    @POST("/voter")
    suspend fun voter(@Body requestBody: VoterRequest): Response<Unit>
}