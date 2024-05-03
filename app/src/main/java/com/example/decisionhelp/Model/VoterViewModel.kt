package com.example.decisionhelp.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decisionhelp.Data.Voter
import com.example.decisionhelp.Data.VoterItem
import com.example.decisionhelp.Data.VoterRequest
import com.example.decisionhelp.Data.VoterThemeRequest
import com.example.decisionhelp.Data.itemResultCheck
import com.example.decisionhelp.Data.itemResultRequest
import com.example.decisionhelp.Data.voterClosedRequest
import com.example.decisionhelp.Data.voterCountRequest
import com.example.decisionhelp.Network.VoterRepository
import kotlinx.coroutines.launch

class VoterViewModel(private val voterRepository: VoterRepository) : ViewModel() {

    private val _voterResult = MutableLiveData<Boolean>()
    val voterResult: LiveData<Boolean>
        get() = _voterResult

    private val _voterThemeResult = MutableLiveData<Boolean>()

    private val _itemResult = MutableLiveData<Boolean>()

    private val _voterCount = MutableLiveData<Boolean>()

    private val _itemResultCheck = MutableLiveData<List<itemResultCheck>>()
    val itemResultCheck: LiveData<List<itemResultCheck>>
        get() = _itemResultCheck

    private val _voters = MutableLiveData<List<Voter>>()
    val voters: MutableLiveData<List<Voter>>
        get() = _voters

    private val _selectedVoter = MutableLiveData<Voter?>(null)
    val selectedVoter: LiveData<Voter?>
        get() = _selectedVoter

    private val _voterItem = MutableLiveData<List<VoterItem>>()
    val votersItem: LiveData<List<VoterItem>>
        get() = _voterItem


    private val _voterClosed = MutableLiveData<Boolean>()

    fun setSelectedVoter(voter: Voter?) {
        _selectedVoter.value = voter
    }
    fun voter(voterCode: String, voterTitle: String, voterDetail: String,
              voterDate: String, voterTime: String, voterWhether: Boolean, id: String) {
        viewModelScope.launch {
            val response = voterRepository.voter(
                VoterRequest(voterCode, voterTitle, voterDetail,
                    voterDate, voterTime, voterWhether, id)
            )
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

    fun getVoterItems(voterCode: String) {
        viewModelScope.launch {
            val response = voterRepository.getVoterItems(voterCode)
            if (response.isSuccessful) {
                _voterItem.value = response.body()
            } else {
                // Handle unsuccessful response
            }
        }
    }

    fun itemResult(voterCode: String,voterItemCode: Int, id: String, result: Boolean) {
        viewModelScope.launch {
            val response = voterRepository.itemResult(
                itemResultRequest(voterCode,voterItemCode, id, result)
            )
            _itemResult.value = response.isSuccessful
        }
    }

    fun voterCount(voterItemCode: Int, count: Int) {
        viewModelScope.launch {
            val response = voterRepository.voterCount(
                voterCountRequest(voterItemCode, count)
            )
            _voterCount.value = response.isSuccessful
        }
    }

    fun itemResultCheck(id: String,voterCode: String) {
        viewModelScope.launch {
            val response = voterRepository.itemResultCheck(id,voterCode)
            if (response.isSuccessful) {
                _itemResultCheck.value = response.body()
            } else {
                // Handle unsuccessful response
            }
        }
    }

    fun voterClosed( voterCode: String,voterDate: String, voterTime: String) {
        viewModelScope.launch {
            val response = voterRepository.voterClosed(
                voterClosedRequest(voterCode, voterDate,voterTime)
            )
            _voterClosed.value = response.isSuccessful
        }
    }
}