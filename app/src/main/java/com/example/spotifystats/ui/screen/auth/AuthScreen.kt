package com.example.spotifystats.ui.screen.auth

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.spotifystats.Values
import com.example.spotifystats.ui.navigation.NavName
import com.example.spotifystats.ui.navigation.Screens
import com.example.spotifystats.ui.theme.CircularBlack
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    context: Context,
    navController: NavHostController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        LoginBtn(context = context) {
            navController.navigate(Screens.Fav.route)
        }
    }
}

@Composable
fun LoginBtn(
    context: Context,
    onClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    Button(
        onClick = {
            customTabRequest(context = context)
            scope.launch {
                delay(3000)
                onClick()
            }
        }
    ) {
        Text("Login")
    }
}

@Composable
fun LogoutBtn(
    context: Context,
    navController: NavHostController,
) {
    val sharedPreferences = context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)
    Button(
        onClick = {
            with(sharedPreferences.edit()) {
                putString("code", null)
                putString("token", null)
                putString("refresh", null)
                apply()
            }
            navController.navigate(NavName.auth)
        },
        colors = ButtonColors(
            containerColor = Color.Red,
            disabledContentColor = Color.White,
            contentColor = Color.White,
            disabledContainerColor = Color.Red
        )
    ) {
        Text(
            text = "LogOut",
            fontFamily = CircularBlack
        )
    }
}

fun customTabRequest(context: Context) {
    val customTabIntent = CustomTabsIntent.Builder().build()
    val url = "https://accounts.spotify.com/authorize"
    val scope = "user-read-private user-read-email user-top-read user-read-recently-played playlist-modify-public playlist-modify-private"

    val urlConstructor = Uri.parse(url)
        .buildUpon()
        .appendQueryParameter("client_id", Values.client_id)
        .appendQueryParameter("response_type", "code")
        .appendQueryParameter("scope", scope)
        .appendQueryParameter("redirect_uri", Values.redirect_uri)

    customTabIntent.launchUrl(
        context,
        Uri.parse(urlConstructor.toString())
    )
}