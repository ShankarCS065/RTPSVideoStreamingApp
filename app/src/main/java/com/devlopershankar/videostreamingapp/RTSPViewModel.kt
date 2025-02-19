package com.devlopershankar.videostreamingapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RTSPViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStoreManager = DataStoreManager(application)

    private val _rtspUrl = MutableStateFlow("")
    val rtspUrl = _rtspUrl.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreManager.lastRtspUrl.collect { url ->
                url?.let { _rtspUrl.value = it }
            }
        }
    }

    fun saveRtspUrl(url: String) {
        viewModelScope.launch {
            dataStoreManager.saveRtspUrl(url)
            _rtspUrl.value = url
        }
    }
}