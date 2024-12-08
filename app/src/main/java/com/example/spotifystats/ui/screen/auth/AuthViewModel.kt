package com.example.spotifystats.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifystats.domain.repository.SpotifyAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val api: SpotifyAuthRepository,
): ViewModel() {

    fun getToken(code: String) {
        viewModelScope.launch {
            api.getAuthToken(code)
        }
    }
}