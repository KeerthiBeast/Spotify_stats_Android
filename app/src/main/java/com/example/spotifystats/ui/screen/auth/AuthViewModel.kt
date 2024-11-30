package com.example.spotifystats.ui.screen.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifystats.domain.repository.SpotifyAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val api: SpotifyAuthRepository,
    @ApplicationContext private val context: Context
): ViewModel() {
    private val _refreshStatus = MutableStateFlow<Int>(-1)
    val refreshStatus = _refreshStatus.asStateFlow()

    fun getToken(code: String) {
        viewModelScope.launch {
            api.getAuthToken(code)
        }
    }

    fun refreshToken() {
        viewModelScope.launch {
            _refreshStatus.value = api.refreshToken()
        }
    }
}