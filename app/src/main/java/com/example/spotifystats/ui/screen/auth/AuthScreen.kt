package com.example.spotifystats.ui.screen.auth

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.spotifystats.Values
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    onClick: () -> Unit
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
            onClick()
        }
    ) {
        Text("Logout")
    }
}

@Composable
fun RefreshToken(
    context: Context,
    onClick: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val sharedPref = context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)
    val scope = rememberCoroutineScope()
    if(sharedPref.getLong("expiresAt", 0) < System.currentTimeMillis()) {
        Log.d("Expires At", sharedPref.getLong("expiresAt", 0).toString())
        viewModel.refreshToken()
    }
    LaunchedEffect(true) {
        scope.launch{
            delay(3000)
            onClick()
        }
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

@Composable
fun AuthenticationChecker(
    context: Context,
    onClick: () -> Unit
) {
    val sharedPref = context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)
    if(sharedPref.getString("token", null) == null) {
        LoginBtn(context = context, onClick = onClick)
    } else {
        RefreshToken(context = context, onClick = onClick)
    }
}