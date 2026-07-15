package com.comicsopentrends.feature.clans.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.comicsopentrends.core.ui.components.FullScreenError
import com.comicsopentrends.core.ui.components.FullScreenLoading
import com.comicsopentrends.domain.model.Clan
import com.comicsopentrends.feature.clans.R
import com.comicsopentrends.feature.clans.list.ClanListContract.Effect
import com.comicsopentrends.feature.clans.list.ClanListContract.Intent
import org.koin.androidx.compose.koinViewModel

@Composable
fun ClanListRoute(
    onNavigateToDetail: (String) -> Unit,
    onShowBadgePreview: (String?, String) -> Unit,
    viewModel: ClanListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is Effect.NavigateToDetail -> onNavigateToDetail(effect.clanTag)
                is Effect.ShowClanBadgePreview -> onShowBadgePreview(effect.imageUrl, effect.clanName)
            }
        }
    }

    ClanListScreen(
        state = state,
        onIntent = viewModel::submitIntent,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ClanListScreen(
    state: ClanListContract.State,
    onIntent: (Intent) -> Unit,
) {
    val listState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { onIntent(Intent.Refresh) },
    )

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = listState.layoutInfo.totalItemsCount
            totalItems > 0 && lastVisible >= totalItems - LOAD_MORE_THRESHOLD
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) onIntent(Intent.LoadNextPage)
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(stringResource(R.string.clan_list_title, state.clans.size)) },
                )
                ClanSearchField(
                    query = state.query,
                    onQueryChange = { onIntent(Intent.Search(it)) },
                    onClear = { onIntent(Intent.ClearSearch) },
                )
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .pullRefresh(pullRefreshState)
                .fillMaxSize(),
        ) {
            when {
                state.isLoading && state.clans.isEmpty() -> FullScreenLoading()

                state.errorMessage != null && state.clans.isEmpty() -> FullScreenError(
                    message = state.errorMessage,
                    onRetry = { onIntent(Intent.LoadFirstPage) },
                )

                else -> ClanList(
                    clans = state.clans,
                    isLoadingNextPage = state.isLoadingNextPage,
                    listState = listState,
                    onClanClick = { onIntent(Intent.ClanClicked(it)) },
                    onBadgeClick = { onIntent(Intent.BadgeClicked(it)) },
                )
            }

            PullRefreshIndicator(
                refreshing = state.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@Composable
private fun ClanList(
    clans: List<Clan>,
    isLoadingNextPage: Boolean,
    listState: LazyListState,
    onClanClick: (Clan) -> Unit,
    onBadgeClick: (Clan) -> Unit,
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        items(clans, key = { it.tag }) { clan ->
            ClanListItem(
                clan = clan,
                onClick = { onClanClick(clan) },
                onBadgeClick = { onBadgeClick(clan) },
            )
        }

        if (isLoadingNextPage) {
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(28.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClanSearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = { Text(stringResource(R.string.clan_search_hint)) },
        singleLine = true,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClear) {
                    Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.clan_search_clear))
                }
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

private const val LOAD_MORE_THRESHOLD = 5
