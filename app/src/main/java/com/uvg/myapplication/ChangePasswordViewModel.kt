package com.uvg.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.myapplication.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChangePasswordViewModel(private val repository: UserRepository) : ViewModel() {

    private val _passwordUpdated = MutableStateFlow(false)
    val passwordUpdated: StateFlow<Boolean> = _passwordUpdated

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun changePassword(username: String, newPassword: String) {
        viewModelScope.launch {
            try {
                val success = repository.updatePassword(username, newPassword)
                if (success) {
                    _passwordUpdated.value = true
                } else {
                    _errorMessage.value = "Failed to update password"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            }
        }
    }
}
