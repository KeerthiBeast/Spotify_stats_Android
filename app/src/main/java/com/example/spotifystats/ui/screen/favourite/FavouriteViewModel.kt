package com.example.spotifystats.ui.screen.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifystats.domain.model.Top
import com.example.spotifystats.domain.repository.SpotifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val api: SpotifyRepository
): ViewModel() {

    private val _topTracks = MutableStateFlow<List<Top>>(emptyList())

    private fun getTopTracks(timeRange: String) {
        viewModelScope.launch {
            _topTracks.value = api.getTopTracks(timeRange)
        }
    }

    val topTrack = _topTracks
        .onStart { getTopTracks("short_term") }
        .debounce(1000)
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = _topTracks.value
        )

}