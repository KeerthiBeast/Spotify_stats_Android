package com.example.spotifystats.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.spotifystats.data.network.SpotifyApi
import com.example.spotifystats.domain.model.Availability
import com.example.spotifystats.domain.model.Recent
import com.example.spotifystats.domain.model.Top
import com.example.spotifystats.domain.model.User
import com.example.spotifystats.domain.repository.SpotifyAuthRepository
import com.example.spotifystats.domain.repository.SpotifyRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SpotifyRepositoryImpl @Inject constructor(
    private val api: SpotifyApi,
    private val auth: SpotifyAuthRepository,
    @ApplicationContext private val context: Context
): SpotifyRepository {

    override suspend fun getRecentlyPlayed(): List<Recent> {
        val sharedPref = context
            .getSharedPreferences(
                "app_pref",
                Context.MODE_PRIVATE
            )

        val expiresAt = sharedPref.getLong("expiresAt", 0)
        if (expiresAt < System.currentTimeMillis()) {
            auth.refreshToken()
        }


        try {
            val savedToken = sharedPref.getString("token", null)
            val authorization = "Bearer $savedToken"
            val response = api.getRecentlyPlayed(token = authorization)
            if(response.isSuccessful) {
                val responseBody = response.body()
                val items = responseBody?.toModel()
                return items ?: emptyList()
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Error in API ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Log.d("Error in API", response.toString())
                return emptyList()
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Error in API $e",
                    Toast.LENGTH_SHORT
                ).show()
            }
            Log.d("Error in API", e.toString())
            return emptyList()
        }
    }

    override suspend fun getTopTracks(timeRange: String): List<Top> {
        val sharedPref = context
            .getSharedPreferences(
                "app_pref",
                Context.MODE_PRIVATE
            )

        val expiresAt = sharedPref.getLong("expiresAt", 0)
        if (expiresAt < System.currentTimeMillis()) {
            auth.refreshToken()
        }


        try {
            val savedToken = sharedPref.getString("token", null)
            val authorization = "Bearer $savedToken"
            val response = api.getTopTracks(
                token = authorization,
                timeRange = timeRange
            )
            if(response.isSuccessful) {
                val responseBody = response.body()
                val items = responseBody?.toModel()
                return items ?: emptyList()
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Error in API ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Log.d("Error in API", response.toString())
                return emptyList()
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Error in API $e",
                    Toast.LENGTH_LONG
                ).show()
            }
            return emptyList()
        }
    }

    override suspend fun getUser(): User {
        val sharedPref = context
            .getSharedPreferences(
                "app_pref",
                Context.MODE_PRIVATE
            )

        val expiresAt = sharedPref.getLong("expiresAt", 0)
        if (expiresAt < System.currentTimeMillis()) {
            auth.refreshToken()
        }


        try {
            val savedToken = sharedPref.getString("token", null)
            val authorization = "Bearer $savedToken"
            val response = api.getUser(token = authorization)
            if(response.isSuccessful) {
                val responseBody = response.body()
                val item = responseBody!!.toModel()
                return item
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Error in API ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Log.d("Error in API", response.toString())
                return User()
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Error in API $e",
                    Toast.LENGTH_LONG
                ).show()
            }
            return User()
        }
    }

    override suspend fun getAvailability(id: String): Availability {
        val sharedPref = context
            .getSharedPreferences(
                "app_pref",
                Context.MODE_PRIVATE
            )

        val expiresAt = sharedPref.getLong("expiresAt", 0)
        if (expiresAt < System.currentTimeMillis()) {
                auth.refreshToken()
        }

        try {
            val savedToken = sharedPref.getString("token", null)
            val authorization = "Bearer $savedToken"
            val response = api.getAvailability(
                token = authorization,
                id = id
            )
            if(response.isSuccessful) {
                val responseBody = response.body()
                val item = responseBody?.toModel()
                return item ?: Availability()
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Error in API ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Log.d("Error in API", response.toString())
                return Availability()
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Error in API $e",
                    Toast.LENGTH_LONG
                ).show()
            }
            Log.d("Error in API", e.toString())
            return Availability()
        }
    }
}