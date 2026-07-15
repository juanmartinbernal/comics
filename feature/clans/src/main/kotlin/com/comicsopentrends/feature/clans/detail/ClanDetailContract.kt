package com.comicsopentrends.feature.clans.detail

import com.comicsopentrends.core.mvi.MviEffect
import com.comicsopentrends.core.mvi.MviIntent
import com.comicsopentrends.core.mvi.MviState
import com.comicsopentrends.domain.model.Clan

object ClanDetailContract {

    data class State(
        val clan: Clan? = null,
        val isLoading: Boolean = true,
        val errorMessage: String? = null,
    ) : MviState

    sealed interface Intent : MviIntent {
        data class Load(val clanTag: String) : Intent
        data object BadgeClicked : Intent
    }

    sealed interface Effect : MviEffect {
        data class ShowClanBadgePreview(val imageUrl: String?, val clanName: String) : Effect
    }
}
