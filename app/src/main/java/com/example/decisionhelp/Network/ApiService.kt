package com.example.decisionhelp.Network

import com.example.decisionhelp.Data.IdCheckRequest
import com.example.decisionhelp.Data.LoginRequest
import com.example.decisionhelp.Data.SignupRequest
import com.example.decisionhelp.Data.Voter
import com.example.decisionhelp.Data.VoterRequest
import com.example.decisionhelp.Data.VoterThemeRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
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

    @POST("/voterTheme")
    suspend fun voterTheme(@Body requestBody: VoterThemeRequest): Response<String>

    @GET("voters")
    suspend fun getVoters(): Response<List<Voter>>
}