package com.tailorapp.stitchup.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("auth_prefs")
@Singleton
class AuthDataStore @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    suspend fun saveToken(token: String){
        Log.d("###", "Saving token: $token")
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    fun getToken() : Flow<String?> {
        return context.dataStore.data.map { prefs ->
            val token = prefs[TOKEN_KEY]
            Log.d("###", "Token read: $token")
            token
        }
    }

    suspend fun clearToken() {
        Log.d("###", "Clearing token")
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }
}