package com.example.decisionhelp.Network

import com.example.decisionhelp.Data.Voter
import com.example.decisionhelp.Data.VoterItem
import com.example.decisionhelp.Data.VoterRequest
import com.example.decisionhelp.Data.VoterThemeRequest
import com.example.decisionhelp.Data.itemResultCheck
import com.example.decisionhelp.Data.itemResultRequest
import com.example.decisionhelp.Data.voterClosedRequest
import com.example.decisionhelp.Data.voterCountRequest
import retrofit2.Response

class VoterRepository(private val apiService: ApiService) {
    suspend fun voter(voterRequest: VoterRequest) = apiService.voter(voterRequest)

    suspend fun voterTheme(voterThemeRequest: VoterThemeRequest) = apiService.voterTheme(voterThemeRequest)

    suspend fun getVoters(): Response<List<Voter>> {
        return apiService.getVoters()
    }


    suspend fun getVoterItems(voterCode: String): Response<List<VoterItem>> {
        return apiService.getVoterItems(voterCode)
    }

    suspend fun itemResult(itemresult: itemResultRequest) = apiService.itemResult(itemresult)

    suspend fun voterCount(votercount: voterCountRequest) = apiService.voterCount(votercount)

    suspend fun itemResultCheck(id: String): Response<List<itemResultCheck>> {
        return apiService.itemResultCheck(id)
    }

    suspend fun voterClosed(voterclosed: voterClosedRequest) = apiService.voterClosed(voterclosed)
}
