package com.anilist.aniexplorer.model.domain

data class Character(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val role: String?
)
