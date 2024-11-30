package com.example.spotifystats.data.network.dto

import com.example.spotifystats.domain.model.Recent

data class RecentDto(
    val cursors: Cursors,
    val href: String,
    val items: List<Item>,
    val limit: Int,
    val next: String
) {
    fun toModel(): List<Recent> = items.map {
        Recent(
            artistName = it.track.artists.first().name,
            songName = it.track.name
        )
    }

    data class Cursors(
        val after: String,
        val before: String
    )

    data class Item(
        val context: Context,
        val played_at: String,
        val track: Track
    ) {
        data class Context(
            val external_urls: ExternalUrls,
            val href: String,
            val type: String,
            val uri: String
        ) {
            data class ExternalUrls(
                val spotify: String
            )
        }

        data class Track(
            val album: Album,
            val artists: List<Artist>,
            val available_markets: List<String>,
            val disc_number: Int,
            val duration_ms: Int,
            val explicit: Boolean,
            val external_ids: ExternalIds,
            val external_urls: ExternalUrls,
            val href: String,
            val id: String,
            val is_local: Boolean,
            val name: String,
            val popularity: Int,
            val preview_url: Any,
            val track_number: Int,
            val type: String,
            val uri: String
        ) {
            data class Album(
                val album_type: String,
                val artists: List<Artist>,
                val available_markets: List<String>,
                val external_urls: ExternalUrls,
                val href: String,
                val id: String,
                val images: List<Image>,
                val name: String,
                val release_date: String,
                val release_date_precision: String,
                val total_tracks: Int,
                val type: String,
                val uri: String
            ) {
                data class Artist(
                    val external_urls: ExternalUrls,
                    val href: String,
                    val id: String,
                    val name: String,
                    val type: String,
                    val uri: String
                ) {
                    data class ExternalUrls(
                        val spotify: String
                    )
                }

                data class ExternalUrls(
                    val spotify: String
                )

                data class Image(
                    val height: Int,
                    val url: String,
                    val width: Int
                )
            }

            data class Artist(
                val external_urls: ExternalUrls,
                val href: String,
                val id: String,
                val name: String,
                val type: String,
                val uri: String
            ) {
                data class ExternalUrls(
                    val spotify: String
                )
            }

            data class ExternalIds(
                val isrc: String
            )

            data class ExternalUrls(
                val spotify: String
            )
        }
    }
}