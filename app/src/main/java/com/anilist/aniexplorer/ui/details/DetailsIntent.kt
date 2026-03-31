package com.anilist.aniexplorer.ui.details

import androidx.annotation.StringRes

sealed class DetailsIntent {
    object RetryLoad : DetailsIntent()
    object OnBackClick : DetailsIntent()
    data class OnActionClick(@StringRes val actionResId: Int) : DetailsIntent()
}
