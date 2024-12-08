package com.example.spotifystats.ui.screen.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.spotifystats.ui.screen.auth.LogoutBtn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    context: Context,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text ("Profile")
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
            val user by viewModel.user.collectAsState()

            if(user.country != 0) {
                Image(
                    painter = painterResource(id = user.country),
                    contentDescription = "Country Flag",
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
            if(user.profile.isNotBlank()) {
                AsyncImage(
                    model = user.profile,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .height(200.dp)
                        .width(200.dp)
                        .clip(shape = CircleShape)
                )
            }
            Text(
                text = user.name,
                modifier = Modifier
                    .padding(6.dp)
            )
            Text(
                text = user.product.uppercase(),
                color = if (user.product == "premium") Color.Green else Color.Red,
                modifier = Modifier
                    .padding(16.dp)
            )

            LogoutBtn(
                context = context,
                navController = navController
            )
        }
    }
}