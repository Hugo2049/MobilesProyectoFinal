package com.uvg.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.myapplication.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CheckUserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _userExists = MutableStateFlow(false)
    val userExists: StateFlow<Boolean> = _userExists

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun checkUser(username: String) {
        viewModelScope.launch {
            try {
                val exists = repository.checkUsernameExists(username)
                if (exists) {
                    _userExists.value = true
                } else {
                    _errorMessage.value = "User not found"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            }
        }
    }
}
