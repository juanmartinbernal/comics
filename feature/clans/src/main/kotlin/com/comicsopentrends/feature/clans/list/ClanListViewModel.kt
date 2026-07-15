package com.comicsopentrends.feature.clans.list

import androidx.lifecycle.viewModelScope
import com.comicsopentrends.core.common.AppResult
import com.comicsopentrends.core.mvi.MviViewModel
import com.comicsopentrends.domain.usecase.GetClansUseCase
import com.comicsopentrends.domain.usecase.SearchClansUseCase
import com.comicsopentrends.feature.clans.list.ClanListContract.Effect
import com.comicsopentrends.feature.clans.list.ClanListContract.Intent
import com.comicsopentrends.feature.clans.list.ClanListContract.State
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ClanListViewModel(
    private val getClansUseCase: GetClansUseCase,
    private val searchClansUseCase: SearchClansUseCase,
) : MviViewModel<Intent, State, Effect>(State()) {

    private val queryFlow = MutableStateFlow("")

    init {
        submitIntent(Intent.LoadFirstPage)
        observeSearchQuery()
    }

    override suspend fun handleIntent(intent: Intent) {
        when (intent) {
            Intent.LoadFirstPage -> loadFirstPage()
            Intent.LoadNextPage -> loadNextPage()
            Intent.Refresh -> refresh()
            is Intent.Search -> onSearchChanged(intent.query)
            Intent.ClearSearch -> onSearchChanged("")
            is Intent.ClanClicked -> sendEffect { Effect.NavigateToDetail(intent.clan.tag) }
            is Intent.BadgeClicked -> sendEffect {
                Effect.ShowClanBadgePreview(intent.clan.badgeUrls.large, intent.clan.name)
            }
        }
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            @OptIn(FlowPreview::class)
            queryFlow
                .debounce(SEARCH_DEBOUNCE_MILLIS)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.length >= State.MIN_QUERY_LENGTH) {
                        performSearch(query)
                    } else if (query.isEmpty()) {
                        loadFirstPage()
                    }
                }
        }
    }

    private suspend fun onSearchChanged(query: String) {
        setState { copy(query = query) }
        queryFlow.value = query
    }

    private suspend fun loadFirstPage() {
        setState { copy(isLoading = true, errorMessage = null) }
        when (val result = getClansUseCase()) {
            is AppResult.Success -> setState {
                copy(
                    clans = result.data.clans,
                    nextCursor = result.data.nextCursor,
                    canLoadMore = result.data.nextCursor != null,
                    isLoading = false,
                )
            }

            is AppResult.Error -> setState { copy(isLoading = false, errorMessage = result.message) }
        }
    }

    private suspend fun loadNextPage() {
        val state = currentState
        if (state.isLoadingNextPage || !state.canLoadMore || state.isSearching) return

        setState { copy(isLoadingNextPage = true) }
        when (val result = getClansUseCase(state.nextCursor)) {
            is AppResult.Success -> setState {
                copy(
                    clans = clans + result.data.clans,
                    nextCursor = result.data.nextCursor,
                    canLoadMore = result.data.nextCursor != null,
                    isLoadingNextPage = false,
                )
            }

            is AppResult.Error -> setState { copy(isLoadingNextPage = false, errorMessage = result.message) }
        }
    }

    private suspend fun refresh() {
        setState { copy(isRefreshing = true, errorMessage = null) }
        val result = if (currentState.isSearching) {
            searchClansUseCase(currentState.query)
        } else {
            getClansUseCase()
        }
        when (result) {
            is AppResult.Success -> setState {
                copy(
                    clans = result.data.clans,
                    nextCursor = result.data.nextCursor,
                    canLoadMore = result.data.nextCursor != null && !isSearching,
                    isRefreshing = false,
                )
            }

            is AppResult.Error -> setState { copy(isRefreshing = false, errorMessage = result.message) }
        }
    }

    private suspend fun performSearch(query: String) {
        setState { copy(isLoading = true, errorMessage = null) }
        when (val result = searchClansUseCase(query)) {
            is AppResult.Success -> setState {
                copy(clans = result.data.clans, isLoading = false, canLoadMore = false)
            }

            is AppResult.Error -> setState { copy(isLoading = false, errorMessage = result.message) }
        }
    }

    private companion object {
        const val SEARCH_DEBOUNCE_MILLIS = 350L
    }
}
