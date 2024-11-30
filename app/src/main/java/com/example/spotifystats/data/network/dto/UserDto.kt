package com.example.spotifystats.data.network.dto

import com.example.spotifystats.domain.model.User

data class UserDto(
    val country: String,
    val display_name: String,
    val email: String,
    val explicit_content: ExplicitContent,
    val external_urls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<Image>,
    val product: String,
    val type: String,
    val uri: String
) {
    fun toModel(): User = User(
        id = id,
        name = display_name,
        profile = images[1].url,
        product = product
    )
    data class ExplicitContent(
        val filter_enabled: Boolean,
        val filter_locked: Boolean
    )

    data class ExternalUrls(
        val spotify: String
    )

    data class Followers(
        val href: Any,
        val total: Int
    )

    data class Image(
        val height: Int,
        val url: String,
        val width: Int
    )
}