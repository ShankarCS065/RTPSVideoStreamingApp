package com.devlopershankar.videostreamingapp

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class DataStoreManager(private val context: Context) {

    companion object {
        private val RTSP_URL_KEY = stringPreferencesKey("rtsp_url")
    }

    val lastRtspUrl: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[RTSP_URL_KEY] }

    suspend fun saveRtspUrl(url: String) {
        context.dataStore.edit { preferences ->
            preferences[RTSP_URL_KEY] = url
        }
    }
}