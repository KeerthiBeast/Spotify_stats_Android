package com.example.spotifystats.data.di

import android.content.Context
import com.example.spotifystats.data.network.SpotifyApi
import com.example.spotifystats.data.network.SpotifyAuthApi
import com.example.spotifystats.data.repository.SpotifyAuthRepositoryImpl
import com.example.spotifystats.data.repository.SpotifyRepositoryImpl
import com.example.spotifystats.domain.repository.SpotifyAuthRepository
import com.example.spotifystats.domain.repository.SpotifyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    const val Base = "https://accounts.spotify.com/api/"
    const val BaseApi = "https://api.spotify.com/v1/"

    @Provides
    @Singleton
    fun provideSpotifyAuthApi(): SpotifyAuthApi =
        Retrofit.Builder()
            .baseUrl(Base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyAuthApi::class.java)

    @Provides
    @Singleton
    fun provideSpotifyApi(): SpotifyApi =
        Retrofit.Builder()
            .baseUrl(BaseApi)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyApi::class.java)

    @Provides
    @Singleton
    fun providesSpotifyRepository(
        api: SpotifyApi,
        auth: SpotifyAuthRepository,
        @ApplicationContext context: Context
    ): SpotifyRepository = SpotifyRepositoryImpl(api, auth, context)

    @Provides
    @Singleton
    fun providesSpotifyAuthRepository(
        api: SpotifyAuthApi,
        @ApplicationContext context: Context
    ): SpotifyAuthRepository = SpotifyAuthRepositoryImpl(api, context)
}