package com.example.spotifystats.ui.screen.favourite

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil3.compose.AsyncImage
import com.example.spotifystats.R
import com.example.spotifystats.domain.model.Top

@Composable
fun ExpandableCards(
    index: Int,
    favourite: Top,
    context: Context
) {
    var expandedState by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            ),
        onClick = { expandedState = !expandedState }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if(!expandedState) {
                    Text(
                        text = "#${index+1} ${favourite.songName}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                    )
                }
                if(expandedState) {
                    Text(
                        text = favourite.songName,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                    )
                    AsyncImage(
                        model = favourite.imageUrl,
                        contentDescription = "Song Image",
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(shape = CircleShape)
                            .height(65.dp)
                            .width(65.dp)
                    )
                }
            }

            if(expandedState) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = favourite.artistName,
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(1f)
                        )
                        Text(
                            text = "#${index+1}",
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("spotify:track:${getId(favourite.songUrl)}")
                            )
                            startActivity(context, intent, null)
                        },
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.spcolor),
                            contentDescription = "Play Song",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                        )
                    }
                }
            }
        }
    }
}

fun getId(url: String): String {
    return url.substringAfter("track/").substringBefore("?")
}