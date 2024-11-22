package com.uvg.myapplication.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun checkUserCredentials(username: String, password: String): Boolean {
        val result = db.collection("users")
            .whereEqualTo("username", username)
            .whereEqualTo("password", password)
            .get()
            .await()
        return !result.isEmpty
    }

    suspend fun checkUsernameExists(username: String): Boolean {
        val result = db.collection("users")
            .whereEqualTo("username", username)
            .get()
            .await()
        return !result.isEmpty
    }

    suspend fun updatePassword(username: String, newPassword: String): Boolean {
        val result = db.collection("users")
            .whereEqualTo("username", username)
            .get()
            .await()
        if (!result.isEmpty) {
            val userId = result.documents[0].id
            db.collection("users").document(userId)
                .update("password", newPassword)
                .await()
            return true
        }
        return false
    }
}
