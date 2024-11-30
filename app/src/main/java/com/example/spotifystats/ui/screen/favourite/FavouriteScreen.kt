package com.example.spotifystats.ui.screen.favourite

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.spotifystats.ui.screen.auth.AuthScreen

@Composable
fun FavouriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel(),
    context: Context,
    paddingValues: PaddingValues
) {
    val topTracks by viewModel.topTrack.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item() {
            Text("This is the list")
        }
        items(topTracks) { track ->
            Text(track.songName)
        }
    }
}