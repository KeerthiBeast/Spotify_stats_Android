package com.example.spotifystats.ui.navigation

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.spotifystats.ui.screen.auth.AuthScreen
import com.example.spotifystats.ui.screen.favourite.FavouriteScreen
import com.example.spotifystats.ui.screen.profile.UserScreen

object NavName {
    const val home = "Blob"
    const val sync = "Sync"
    const val about = "Settings"
    const val profile = "Profile"
}

//Main navigation function
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Navigation(activity: ComponentActivity, navController: NavHostController, startDest: String, paddingValues: PaddingValues) {
    NavHost(navController = navController, startDestination = startDest) {
        composable(NavName.sync) {
            UserScreen(context = activity, paddingValues = paddingValues)
        }
        composable(NavName.home) {
            AuthScreen(context = activity, paddingValues = paddingValues)
        }
        composable(NavName.about) {
            FavouriteScreen(context = activity, paddingValues =  paddingValues)
        }
        composable(NavName.profile) {
            FavouriteScreen(context = activity, paddingValues =  paddingValues)
        }
    }
}

//Sealed class to create navigation routes with objects of the class type Screens
sealed class Screens(val route: String,
                     val selected: ImageVector,
                     val unselected: ImageVector,
                     val label: String) {

    data object Home: Screens(
        route = NavName.home,
        selected = Icons.Default.Favorite,
        unselected = Icons.Default.FavoriteBorder,
        label = NavName.home
    )
    data object Sync: Screens(
        route = NavName.sync,
        selected = Icons.Default.Add,
        unselected = Icons.Default.AddCircle,
        label = NavName.sync
    )
    data object About: Screens(
        route = NavName.about,
        selected = Icons.Default.Settings,
        unselected = Icons.Default.Settings,
        label = NavName.about
    )
    data object Profile: Screens(
        route = NavName.profile,
        selected = Icons.Default.AccountCircle,
        unselected = Icons.Default.AccountCircle,
        label = NavName.profile
    )
}