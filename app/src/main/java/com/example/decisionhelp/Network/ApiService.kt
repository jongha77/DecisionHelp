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
    // 회원 가입
    @POST("/signup")
    suspend fun signUp(@Body requestBody: SignupRequest): Response<Unit>

    // 회원 가입 시 아이디 중복 체크
    @POST("/idcheck")
    suspend fun checkId(@Body request: IdCheckRequest): Response<Boolean>

    // 로그인
    @POST("/login")
    suspend fun login(@Body request: LoginRequest): Response<Boolean>

    // 투표 정보 등록
    @POST("/voter")
    suspend fun voter(@Body requestBody: VoterRequest): Response<Unit>

    // 투표의 항목 정보 등록
    @POST("/voterTheme")
    suspend fun voterTheme(@Body requestBody: VoterThemeRequest): Response<String>

    // 등록한 투표들을 가져옴
    @GET("voters")
    suspend fun getVoters(): Response<List<Voter>>

    // 등록한 투표의 코드를 통해 해당 투표의 항목을 가져옴
    @GET("/voteritem")
    suspend fun getVoterItems(@Query("voterCode") voterCode: String): Response<List<VoterItem>>

    // 누가 어느 항목에 투표했는지 정보를 등록
    @POST("/itemresult")
    suspend fun itemResult(@Body requestBody: itemResultRequest): Response<Unit>

    // 항목 당 투표 수
    @POST("/votercount")
    suspend fun voterCount(@Body requestBody: voterCountRequest): Response<Unit>

    // 사용자가 무슨 항목에 투표했는지를 가져옴
    @GET("/itemresultcheck")
    suspend fun itemResultCheck(@Query("id") id: String): Response<List<itemResultCheck>>

    // 투표를 게시한 사용자가 투표마감을 하기 위한 서버
    @POST("/voterclosed")
    suspend fun voterClosed(@Body requestBody: voterClosedRequest): Response<Unit>

}