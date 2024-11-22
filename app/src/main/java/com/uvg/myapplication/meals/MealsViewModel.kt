package com.uvg.myapplication.meals

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class MealsViewModel(private val preferences: SharedPreferences) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // Estado para las comidas
    private val _meals = MutableStateFlow<List<String>>(emptyList())
    val meals: StateFlow<List<String>> = _meals

    // Estado del día seleccionado
    private val _selectedDay = MutableStateFlow<LocalDate>(LocalDate.now())
    val selectedDay: StateFlow<LocalDate> = _selectedDay

    init {
        loadMealsForDay(_selectedDay.value)
    }

    fun loadMealsForDay(date: LocalDate) {
        viewModelScope.launch {
            val dayKey = "día_${date.dayOfMonth}"
            db.collection("users")
                .document("rZ3FtLee4lvwfgZVCnbu")
                .collection("entries")
                .document(dayKey)
                .collection("meals")
                .get()
                .addOnSuccessListener { documents ->
                    val loadedMeals = documents.map { it.getString("name") ?: "Unknown Meal" }
                    _meals.value = loadedMeals
                    saveMealsToCache(loadedMeals)
                }
                .addOnFailureListener {
                    _meals.value = listOf("Error loading meals")
                }
        }
    }

    fun updateSelectedDay(date: LocalDate) {
        _selectedDay.value = date
        loadMealsForDay(date)
    }

    fun loadMealsForTomorrow() {
        val tomorrow = LocalDate.now().plusDays(1)
        updateSelectedDay(tomorrow)
    }

    fun loadMealsForDayAfterTomorrow() {
        val dayAfterTomorrow = LocalDate.now().plusDays(2)
        updateSelectedDay(dayAfterTomorrow)
    }

    private fun saveMealsToCache(meals: List<String>) {
        preferences.edit()
            .putStringSet("meals", meals.toSet())
            .apply()
    }

    private fun loadMealsFromCache(): List<String> {
        return preferences.getStringSet("meals", emptySet())?.toList() ?: emptyList()
    }
}