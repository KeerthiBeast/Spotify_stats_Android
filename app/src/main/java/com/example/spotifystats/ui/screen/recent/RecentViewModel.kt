package com.example.spotifystats.ui.screen.recent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifystats.domain.model.Recent
import com.example.spotifystats.domain.repository.SpotifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentViewModel @Inject constructor(
    private val api: SpotifyRepository
): ViewModel() {
    private val _recentTracks = MutableStateFlow<List<Recent>>(emptyList())

    private fun getRecentTracks() {
        viewModelScope.launch {
            _recentTracks.value = api.getRecentlyPlayed()
        }
    }

    val recentTracks = _recentTracks
        .onStart { getRecentTracks() }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = _recentTracks.value
        )
}