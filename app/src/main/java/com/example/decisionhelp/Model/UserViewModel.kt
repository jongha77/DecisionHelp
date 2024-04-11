package com.example.decisionhelp.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decisionhelp.Data.SignupRequest
import com.example.decisionhelp.Network.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _signupResult = MutableLiveData<Boolean>()
    val signupResult: LiveData<Boolean>
        get() = _signupResult

    private val _idCheckResult = MutableLiveData<Boolean>()
    val idCheckResult: LiveData<Boolean>
        get() = _idCheckResult

    fun signUp(id: String, password: String) {
        viewModelScope.launch {
            val response = userRepository.signUp(SignupRequest(id, password))
            _signupResult.value = response.isSuccessful
        }
    }

    fun checkId(id: String) {
        viewModelScope.launch {
            val idExists = userRepository.checkId(id)
            _idCheckResult.value = idExists
        }
    }
}