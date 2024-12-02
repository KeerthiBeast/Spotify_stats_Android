package com.example.spotifystats.ui.screen.availability

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifystats.domain.model.Availability
import com.example.spotifystats.domain.repository.SpotifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvailabilityViewModel @Inject constructor(
    private val api: SpotifyRepository
): ViewModel(){
    private val _availability = MutableStateFlow<Availability>(Availability())
    val availability = _availability.asStateFlow()

    fun getAvailability(id: String){
        viewModelScope.launch {
            _availability.value = api.getAvailability(id)
        }
    }
}