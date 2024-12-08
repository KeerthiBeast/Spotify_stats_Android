package com.example.spotifystats.ui.screen.availability

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.spotifystats.Values
import com.example.spotifystats.domain.model.Availability
import com.example.spotifystats.ui.theme.CircularBlack

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailabilityScreen(
    viewModel: AvailabilityViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    var showSearchBar by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Availability")
                },
                actions = {
                    IconToggleButton(
                        checked = showSearchBar,
                        onCheckedChange = { showSearchBar = it },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Toggle"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            val availability by viewModel.availability.collectAsState()
            var songId by remember { mutableStateOf("") }
            var isEnabled by remember { mutableStateOf(false) }

            if (showSearchBar) {
                OutlinedTextField(
                    value = songId,
                    onValueChange = {
                        songId = it
                        isEnabled = songId.isNotEmpty()
                    },
                    singleLine = true,
                    label = { Text("Song ID") },
                    shape = MaterialTheme.shapes.large,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Song ID"
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                val id = getId(songId)
                                viewModel.getAvailability(id)
                                songId = ""
                                isEnabled = false
                            },
                            enabled = isEnabled
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
            if(availability.image.isNotBlank()) {
                AsyncImage(
                    model = availability.image,
                    contentDescription = "Song cover",
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(shape = CircleShape)
                )
            }
            Spacer(modifier = Modifier.size(7.dp))
            Text(
                text = availability.name,
                fontFamily = CircularBlack,
                color = Color.Green,
                modifier = Modifier
                    .padding(6.dp)
            )

            if (availability.countries.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(availability.countries) { country ->
                        CountryWithFlags(country)
                    }
                }
            }
        }
    }
}

fun getId(url: String): String {
    return url.substringAfter("track/").substringBefore("?")
}

@Composable
fun CountryWithFlags(country: Availability.Country) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = country.flag),
            contentDescription = country.name,
            modifier = Modifier
                .size(45.dp)
        )
        Text(country.name)
    }
}