package com.example.spotifystats.data.network

import com.example.spotifystats.data.network.dto.RecentDto
import com.example.spotifystats.data.network.dto.TopDto
import com.example.spotifystats.data.network.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyApi {
    @GET("me/top/tracks")
    suspend fun getTopTracks(
        @Header("Authorization") token: String,
        @Query("time_range") timeRange: String
    ): Response<TopDto>

    @GET("me/playlists/recently-played")
    suspend fun getRecentlyPlayed(
        @Header("Authorization") token: String
    ): Response<RecentDto>

    @GET("me")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): Response<UserDto>
}