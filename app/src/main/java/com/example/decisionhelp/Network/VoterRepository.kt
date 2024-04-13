package com.example.decisionhelp.Network

import com.example.decisionhelp.Data.Voter
import com.example.decisionhelp.Data.VoterRequest
import com.example.decisionhelp.Data.VoterThemeRequest
import retrofit2.Response

class VoterRepository(private val apiService: ApiService) {
    suspend fun voter(voterRequest: VoterRequest) = apiService.voter(voterRequest)

    suspend fun voterTheme(voterThemeRequest: VoterThemeRequest) = apiService.voterTheme(voterThemeRequest)

    suspend fun getVoters(): Response<List<Voter>> {
        return apiService.getVoters()
    }
}
