package com.comicsopentrends.feature.clans.list

import com.comicsopentrends.core.mvi.MviEffect
import com.comicsopentrends.core.mvi.MviIntent
import com.comicsopentrends.core.mvi.MviState
import com.comicsopentrends.domain.model.Clan

object ClanListContract {

    data class State(
        val clans: List<Clan> = emptyList(),
        val query: String = "",
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val isLoadingNextPage: Boolean = false,
        val canLoadMore: Boolean = true,
        val nextCursor: String? = null,
        val errorMessage: String? = null,
    ) : MviState {
        val isSearching: Boolean get() = query.length >= MIN_QUERY_LENGTH

        companion object {
            const val MIN_QUERY_LENGTH = 3
        }
    }

    sealed interface Intent : MviIntent {
        data object LoadFirstPage : Intent
        data object LoadNextPage : Intent
        data object Refresh : Intent
        data class Search(val query: String) : Intent
        data object ClearSearch : Intent
        data class ClanClicked(val clan: Clan) : Intent
        data class BadgeClicked(val clan: Clan) : Intent
    }

    sealed interface Effect : MviEffect {
        data class NavigateToDetail(val clanTag: String) : Effect
        data class ShowClanBadgePreview(val imageUrl: String?, val clanName: String) : Effect
    }
}
