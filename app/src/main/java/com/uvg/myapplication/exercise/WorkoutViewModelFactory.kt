package com.uvg.myapplication.exercise

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WorkoutViewModelFactory(
    private val preferences: SharedPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            return WorkoutViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
