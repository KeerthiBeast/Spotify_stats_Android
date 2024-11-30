package com.example.spotifystats.ui.screen.profile

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UserScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    context: Context,
    paddingValues: PaddingValues
) {
    val user by viewModel.user.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(user.name ?: "No name")
        Text(user.product ?: "Pleb Dev")
    }
}