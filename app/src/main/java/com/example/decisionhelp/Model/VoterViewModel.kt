package com.example.decisionhelp.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decisionhelp.Data.Voter
import com.example.decisionhelp.Data.VoterRequest
import com.example.decisionhelp.Data.VoterThemeRequest
import com.example.decisionhelp.Network.VoterRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class VoterViewModel(private val voterRepository: VoterRepository) : ViewModel() {

    private val _voterResult = MutableLiveData<Boolean>()
    val voterResult: LiveData<Boolean>
        get() = _voterResult

    private val _voterThemeResult = MutableLiveData<Boolean>()
    val voterThemeResult: LiveData<Boolean>
        get() = _voterThemeResult

    private val _voters = MutableLiveData<List<Voter>>()
    val voters: LiveData<List<Voter>>
        get() = _voters

    private val _selectedVoter = MutableLiveData<Voter?>(null)
    val selectedVoter: LiveData<Voter?>
        get() = _selectedVoter

    fun setSelectedVoter(voter: Voter?) {
        _selectedVoter.value = voter
    }


    fun voter(voterCode: String, voterTitle: String, voterDetail: String,
              voterDate: String, voterTime: String, voterWhether: Boolean, id: String) {
        viewModelScope.launch {
            val response = voterRepository.voter(VoterRequest(voterCode, voterTitle, voterDetail,
                voterDate, voterTime, voterWhether, id))
            _voterResult.value = response.isSuccessful
        }
    }

    fun voterTheme(voterCode: String, voterItemDetail: String) {
        viewModelScope.launch {
            val response = voterRepository.voterTheme(VoterThemeRequest(voterCode, voterItemDetail))
            _voterThemeResult.value = response.isSuccessful
        }
    }

    fun getVoters() {
        viewModelScope.launch {
            val response = voterRepository.getVoters()
            if (response.isSuccessful) {
                _voters.value = response.body()
            } else {
                // Handle unsuccessful response
            }
        }
    }
}