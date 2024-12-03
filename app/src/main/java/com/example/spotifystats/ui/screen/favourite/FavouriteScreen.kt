package com.example.spotifystats.ui.screen.favourite

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.spotifystats.ui.screen.auth.AuthenticationChecker
import com.example.spotifystats.ui.theme.JetBrains

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
                },
                actions = {
                    DropDown(viewModel)
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
            if (!canShow) {
                AuthenticationChecker(context) {
                    canShow = true
                }
            } else {
                val topTracks by viewModel.topTrack.collectAsState()
                val title by viewModel.title.collectAsState()

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        Text(
                            text = title,
                            fontFamily = JetBrains,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    itemsIndexed(topTracks) { index, top ->
                        ExpandableCards(index, top, context)
                    }
                }
            }
        }
    }
}