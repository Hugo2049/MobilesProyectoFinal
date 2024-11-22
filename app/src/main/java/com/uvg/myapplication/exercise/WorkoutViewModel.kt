package com.uvg.myapplication.exercise

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

class WorkoutViewModel(private val preferences: SharedPreferences) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // Estado para ejercicios
    private val _exercises = MutableStateFlow<List<String>>(emptyList())
    val exercises: StateFlow<List<String>> = _exercises

    // Estado para un ejercicio específico
    private val _exerciseDetails = MutableStateFlow<Map<String, Any>?>(null)
    val exerciseDetails: StateFlow<Map<String, Any>?> = _exerciseDetails

    // Estado del día seleccionado
    private val _selectedDay = MutableStateFlow<LocalDate>(LocalDate.of(2024, 11, 22))
    val selectedDay: StateFlow<LocalDate> = _selectedDay

    // Estado del mes actual
    private val _currentMonth = MutableStateFlow(YearMonth.now())
    val currentMonth: StateFlow<YearMonth> = _currentMonth

    val daysInMonth: List<LocalDate>
        get() = generateDaysInMonth()

    init {
        loadExercisesForDay(_selectedDay.value.dayOfMonth)
    }

    // Cargar ejercicios desde Firebase
    fun loadExercisesForDay(day: Int) {
        viewModelScope.launch {
            db.collection("users")
                .document("rZ3FtLee4lvwfgZVCnbu")
                .collection("entries")
                .document("día_$day")
                .collection("exercises")
                .get()
                .addOnSuccessListener { documents ->
                    val loadedExercises = documents.map { it.getString("name") ?: "Unknown Exercise" }
                    _exercises.value = loadedExercises
                }
                .addOnFailureListener {
                    _exercises.value = listOf("Error loading exercises")
                }
        }
    }

    // Cargar detalles de un ejercicio específico
    fun loadExerciseDetails(exerciseId: String) {
        viewModelScope.launch {
            db.collection("exercises")
                .document(exerciseId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        _exerciseDetails.value = document.data
                    } else {
                        _exerciseDetails.value = mapOf("error" to "Exercise not found")
                    }
                }
                .addOnFailureListener {
                    _exerciseDetails.value = mapOf("error" to "Failed to load exercise details")
                }
        }
    }

    // Actualizar el día seleccionado
    fun updateSelectedDay(day: LocalDate) {
        _selectedDay.value = day
        loadExercisesForDay(day.dayOfMonth)
    }

    // Navegar al mes siguiente
    fun goToNextMonth() {
        _currentMonth.value = _currentMonth.value.plusMonths(1)
    }

    // Navegar al mes anterior
    fun goToPreviousMonth() {
        _currentMonth.value = _currentMonth.value.minusMonths(1)
    }

    // Generar días para el mes actual
    private fun generateDaysInMonth(): List<LocalDate> {
        val month = _currentMonth.value
        val firstDayOfMonth = month.atDay(1)
        val lastDayOfMonth = month.atEndOfMonth()

        // Añadir días iniciales para cuadrar el calendario
        val startOffset = firstDayOfMonth.dayOfWeek.value % 7
        val startDay = firstDayOfMonth.minusDays(startOffset.toLong())

        return generateSequence(startDay) { it.plusDays(1) }
            .takeWhile { it <= lastDayOfMonth.plusDays((6 - lastDayOfMonth.dayOfWeek.value % 7).toLong()) }
            .toList()
    }

    // Persistir ejercicios localmente
    private fun saveExercisesToCache(exercises: List<String>) {
        preferences.edit()
            .putStringSet("exercises", exercises.toSet())
            .apply()
    }

    // Recuperar ejercicios del caché local (opcional, aquí no se usa directamente)
    private fun loadExercisesFromCache(): List<String> {
        return preferences.getStringSet("exercises", emptySet())?.toList() ?: emptyList()
    }
}
