package com.example.spotifystats.domain.repository

import com.example.spotifystats.domain.model.Recent
import com.example.spotifystats.domain.model.Top
import com.example.spotifystats.domain.model.User

interface SpotifyRepository {
    suspend fun getTopTracks(timeRange: String): List<Top>
    suspend fun getRecentlyPlayed(): List<Recent>
    suspend fun getUser(): User
}