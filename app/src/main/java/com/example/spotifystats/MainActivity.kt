package com.example.spotifystats

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.spotifystats.ui.navigation.TopBottom
import com.example.spotifystats.ui.screen.auth.AuthViewModel
import com.example.spotifystats.ui.theme.SpotifyStatsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class MainActivity : ComponentActivity() {
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpotifyStatsTheme {
                viewModel = hiltViewModel()
                val navController = rememberNavController()
                TopBottom(
                    navController = navController,
                    activity = this
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val uri = intent.data
        if(uri != null && uri.scheme == "spstats" && uri.host == "callback") {
            val code = uri.getQueryParameter("code")
            if (code != null) {
                viewModel.getToken(code)
            }
            this.getSharedPreferences("app_pref", MODE_PRIVATE)
                .edit()
                .putString("code", code)
                .apply()
        }
    }
}