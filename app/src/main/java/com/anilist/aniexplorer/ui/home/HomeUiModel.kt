package com.anilist.aniexplorer.ui.home

import com.anilist.aniexplorer.domain.model.HomeSection

data class HomeUiModel(
    val sections: List<HomeSection> = emptyList()
)
