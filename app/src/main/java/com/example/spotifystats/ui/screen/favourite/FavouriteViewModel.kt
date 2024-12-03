package com.example.spotifystats.ui.screen.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifystats.domain.model.Top
import com.example.spotifystats.domain.repository.SpotifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val api: SpotifyRepository
): ViewModel() {

    private val _topTracks = MutableStateFlow<List<Top>>(emptyList())
    val topTrack = _topTracks.asStateFlow()

    init {
        getTopTracks("short_term")
    }

    fun getTopTracks(timeRange: String) {
        viewModelScope.launch {
            _topTracks.value = api.getTopTracks(timeRange)
        }
    }

}