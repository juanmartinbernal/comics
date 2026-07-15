package com.comicsopentrends.feature.clans.detail

import com.comicsopentrends.core.common.AppResult
import com.comicsopentrends.core.mvi.MviViewModel
import com.comicsopentrends.domain.usecase.GetClanDetailUseCase
import com.comicsopentrends.feature.clans.detail.ClanDetailContract.Effect
import com.comicsopentrends.feature.clans.detail.ClanDetailContract.Intent
import com.comicsopentrends.feature.clans.detail.ClanDetailContract.State

class ClanDetailViewModel(
    private val getClanDetailUseCase: GetClanDetailUseCase,
) : MviViewModel<Intent, State, Effect>(State()) {

    override suspend fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.Load -> load(intent.clanTag)
            Intent.BadgeClicked -> {
                val clan = currentState.clan ?: return
                sendEffect { Effect.ShowClanBadgePreview(clan.badgeUrls.medium, clan.name) }
            }
        }
    }

    private suspend fun load(clanTag: String) {
        setState { copy(isLoading = true, errorMessage = null) }
        when (val result = getClanDetailUseCase(clanTag)) {
            is AppResult.Success -> setState { copy(clan = result.data, isLoading = false) }
            is AppResult.Error -> setState { copy(isLoading = false, errorMessage = result.message) }
        }
    }
}
