package com.example.spotifystats.ui.screen.favourite

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun DropDown(
    viewModel: FavouriteViewModel
) {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableIntStateOf(0)
    }

    val duration = listOf(
        Duration.ShortTerm,
        Duration.MediumTerm,
        Duration.LongTerm
    )

    var title by remember { mutableStateOf(duration[itemPosition.intValue].title) }

    Box {
        IconButton(
            onClick = { isDropDownExpanded.value = true }
        )  {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Drop Down"
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
                        title = duration[itemPosition.intValue].title
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
