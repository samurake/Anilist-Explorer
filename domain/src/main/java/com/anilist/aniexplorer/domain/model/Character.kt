package com.anilist.aniexplorer.domain.model

data class Character(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val role: String?
)
