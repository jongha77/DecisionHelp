package com.example.decisionhelp.Network

import com.example.decisionhelp.Data.IdCheckRequest
import com.example.decisionhelp.Data.LoginRequest
import com.example.decisionhelp.Data.SignupRequest
import com.example.decisionhelp.Data.Voter
import com.example.decisionhelp.Data.VoterItem
import com.example.decisionhelp.Data.VoterRequest
import com.example.decisionhelp.Data.VoterThemeRequest
import com.example.decisionhelp.Data.itemResultCheck
import com.example.decisionhelp.Data.itemResultRequest
import com.example.decisionhelp.Data.voterClosedRequest
import com.example.decisionhelp.Data.voterCountRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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

    @GET("/voteritem")
    suspend fun getVoterItems(@Query("voterCode") voterCode: String): Response<List<VoterItem>>

    @POST("/itemresult")
    suspend fun itemResult(@Body requestBody: itemResultRequest): Response<Unit>

    @POST("/votercount")
    suspend fun voterCount(@Body requestBody: voterCountRequest): Response<Unit>

    @GET("/itemresultcheck")
    suspend fun itemResultCheck(@Query("id") id: String): Response<List<itemResultCheck>>

    @POST("/voterclosed")
    suspend fun voterClosed(@Body requestBody: voterClosedRequest): Response<Unit>
}