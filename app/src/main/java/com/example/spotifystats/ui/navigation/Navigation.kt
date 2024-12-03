package com.example.spotifystats.ui.navigation

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.spotifystats.ui.screen.availability.AvailabilityScreen
import com.example.spotifystats.ui.screen.favourite.FavouriteScreen
import com.example.spotifystats.ui.screen.profile.UserScreen
import com.example.spotifystats.ui.screen.recent.RecentScreen

object NavName {
    const val fav = "Favourites"
    const val recent = "Recent"
    const val avail = "Availability"
    const val profile = "Profile"
}

//Main navigation function
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Navigation(activity: ComponentActivity, navController: NavHostController, startDest: String, paddingValues: PaddingValues) {
    NavHost(navController = navController, startDestination = startDest) {
        composable(NavName.recent) {
            RecentScreen(context = activity, paddingValues = paddingValues)
        }
        composable(NavName.fav) {
            FavouriteScreen(context = activity, paddingValues =  paddingValues)
        }
        composable(NavName.avail) {
            AvailabilityScreen(context = activity, paddingValues = paddingValues)
        }
        composable(NavName.profile) {
            UserScreen(context = activity, paddingValues = paddingValues)
        }
    }
}

//Sealed class to create navigation routes with objects of the class type Screens
sealed class Screens(val route: String,
                     val selected: ImageVector,
                     val unselected: ImageVector,
                     val label: String) {

    data object Fav: Screens(
        route = NavName.fav,
        selected = Icons.Default.Favorite,
        unselected = Icons.Default.FavoriteBorder,
        label = NavName.fav
    )
    data object Recent: Screens(
        route = NavName.recent,
        selected = Icons.Default.PlayArrow,
        unselected = Icons.Default.PlayArrow,
        label = NavName.recent
    )
    data object Avail: Screens(
        route = NavName.avail,
        selected = Icons.Default.Search,
        unselected = Icons.Default.Search,
        label = NavName.avail
    )
    data object Profile: Screens(
        route = NavName.profile,
        selected = Icons.Default.AccountCircle,
        unselected = Icons.Default.AccountCircle,
        label = NavName.profile
    )
}