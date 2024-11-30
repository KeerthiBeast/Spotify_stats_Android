package com.example.spotifystats.domain.repository

interface SpotifyAuthRepository {
    suspend fun getAuthToken(code: String)
    suspend fun refreshToken(): Int
}