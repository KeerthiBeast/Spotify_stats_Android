package com.example.spotifystats.ui.screen.favourite

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.spotifystats.ui.screen.auth.AuthenticationChecker
import com.example.spotifystats.ui.screen.auth.LoginBtn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel(),
    context: Context,
    paddingValues: PaddingValues
) {
    var canShow by remember { mutableStateOf(false)}

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Favourite")
                }
            )
        }
    ) { innerPadding->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            if (canShow == false) {
                AuthenticationChecker(context) {
                    canShow = true
                }
            } else {
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
        }
    }
}