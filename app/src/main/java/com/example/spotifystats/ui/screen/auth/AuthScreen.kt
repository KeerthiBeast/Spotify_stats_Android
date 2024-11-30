package com.example.spotifystats.ui.screen.auth

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.spotifystats.Values

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    context: Context,
    paddingValues: PaddingValues
) {
    val sharedPreferences = context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)

    var code by remember {
        mutableStateOf(
            context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)
                .getString("code", null)
        )
    }
    var refreshToken by remember {
        mutableStateOf(
            context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)
                .getString("refresh", null)
        )
    }
    var token by remember {
        mutableStateOf(
            context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)
                .getString("token", null)
        )
    }

    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        Text(
            text = code ?: "No code"
        )

        Text(
            text = token ?: "No token",
            color = Color.Green
        )

        Text(
            text = refreshToken ?: "No Refresh Token",
            color = Color.Blue
        )

        Button(
            onClick = {
                customTabRequest(context = context)
                code = sharedPreferences.getString("code", "")
                token = sharedPreferences.getString("token", "")
                refreshToken = sharedPreferences.getString("refresh", "")
            }
        ) {
            Text("Login")
        }

        Button(
            onClick = {
                viewModel.refreshToken()
            }
        ) {
            Text("Refresh Test")
        }

        Button(
            onClick = {
                with(sharedPreferences.edit()) {
                    putString("code", "")
                    putString("token", "")
                    putString("refresh", "")
                    apply()
                }
                code = sharedPreferences.getString("code", "")
                token = sharedPreferences.getString("token", "")
                refreshToken = sharedPreferences.getString("refresh", "")
            }
        ) {
            Text("Clear")
        }
    }
}

@Composable
fun CheckToken(
    viewModel: AuthViewModel,
    context: Context,
) {
    val refreshStatus by viewModel.refreshStatus.collectAsState()
    val sharedPref = context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)
    if(sharedPref.getLong("expiresAt", 0) < System.currentTimeMillis()) {
        viewModel.refreshToken()
    }
    if(refreshStatus == -1) {
        customTabRequest(context)
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
