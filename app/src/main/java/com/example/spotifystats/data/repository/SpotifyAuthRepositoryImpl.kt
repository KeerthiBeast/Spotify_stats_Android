package com.example.spotifystats.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.spotifystats.Values
import com.example.spotifystats.data.network.SpotifyAuthApi
import com.example.spotifystats.domain.repository.SpotifyAuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class SpotifyAuthRepositoryImpl @Inject constructor(
    private val api: SpotifyAuthApi,
    @ApplicationContext private val context: Context
): SpotifyAuthRepository {

    override suspend fun getAuthToken(code: String) {
        try {
            val sharedPref = context.getSharedPreferences(
                "app_pref",
                Context.MODE_PRIVATE
            )
            val response = api.getAuthToken(code = code)
            if(response.isSuccessful) {
                val responseBody = response.body()
                val token = responseBody?.access_token
                val refreshToken = responseBody?.refresh_token

                val expiresIn = responseBody?.expires_in
                val expiresAt = System.currentTimeMillis() + expiresIn!! * 1000

                with(sharedPref.edit()) {
                    putString("token", token)
                    putString("refresh", refreshToken)
                    putLong("expiresIn", expiresAt)
                }.apply()

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Success",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Error ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Error $e",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override suspend fun refreshToken() {
        try {
            val sharedPref = context.getSharedPreferences(
                "app_pref",
                Context.MODE_PRIVATE
            )
            val response = api.refreshAuthToken(
                refreshToken = sharedPref.getString("refresh", "").toString()
            )
            if(response.isSuccessful) {
                val responseBody = response.body()
                val token = responseBody?.access_token

                val expiresIn = responseBody?.expires_in
                val expiresAt = System.currentTimeMillis() + expiresIn!! * 1000

                with(sharedPref.edit()) {
                    putString("token", token)
                    putLong("expiresIn", expiresAt)
                }.apply()

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Success",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Error ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Error $e",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}