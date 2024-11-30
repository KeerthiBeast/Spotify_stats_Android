package com.example.spotifystats.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifystats.domain.model.User
import com.example.spotifystats.domain.repository.SpotifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val api: SpotifyRepository
): ViewModel() {
    private val _userProfile = MutableStateFlow<User>(User())

    private fun getUser() {
        viewModelScope.launch {
            _userProfile.value = api.getUser()
        }
    }

    val user = _userProfile
        .onStart { getUser() }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = _userProfile.value
        )
}