package com.example.decisionhelp.Network

import com.example.decisionhelp.Data.VoterRequest
import com.example.decisionhelp.Data.VoterThemeRequest

class VoterRepository(private val apiService: ApiService) {
    suspend fun voter(voterRequest: VoterRequest) = apiService.voter(voterRequest)

    suspend fun voterTheme(voterThemeRequest: VoterThemeRequest) = apiService.voterTheme(voterThemeRequest)
}