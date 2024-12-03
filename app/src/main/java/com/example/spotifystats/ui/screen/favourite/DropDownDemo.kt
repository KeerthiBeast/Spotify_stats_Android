package com.example.spotifystats.ui.screen.favourite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spotifystats.ui.theme.JetBrains

@Composable
fun DropDown(
    viewModel: FavouriteViewModel
) {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = rememberSaveable {
        mutableIntStateOf(0)
    }

    val duration = listOf(
        Duration.ShortTerm,
        Duration.MediumTerm,
        Duration.LongTerm
    )

    Box {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isDropDownExpanded.value = true
            }
        ) {
            Text(
                text = duration[itemPosition.intValue].title,
                fontFamily = JetBrains,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(16.dp)
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "DropDown Icon"
            )
        }
        DropdownMenu(
            expanded = isDropDownExpanded.value,
            onDismissRequest = {
                isDropDownExpanded.value = false
            }) {
            duration.forEachIndexed { index, username ->
                DropdownMenuItem(text = {
                    Text(text = username.title)
                },
                    onClick = {
                        isDropDownExpanded.value = false
                        itemPosition.intValue = index
                        viewModel.getTopTracks(username.duration)
                    })
            }
        }
    }
}

sealed class Duration(
    val title: String,
    val duration: String,
) {
    data object ShortTerm : Duration("4 Weeks", "short_term")
    data object MediumTerm : Duration("6 Months", "medium_term")
    data object LongTerm : Duration("12 Months", "long_term")
}
