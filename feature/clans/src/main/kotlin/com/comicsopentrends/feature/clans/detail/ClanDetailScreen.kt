package com.comicsopentrends.feature.clans.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.comicsopentrends.core.ui.components.CircleNetworkImage
import com.comicsopentrends.core.ui.components.FullScreenError
import com.comicsopentrends.core.ui.components.FullScreenLoading
import com.comicsopentrends.domain.model.Clan
import com.comicsopentrends.feature.clans.R
import com.comicsopentrends.feature.clans.detail.ClanDetailContract.Effect
import com.comicsopentrends.feature.clans.detail.ClanDetailContract.Intent
import org.koin.androidx.compose.koinViewModel

@Composable
fun ClanDetailRoute(
    clanTag: String,
    onNavigateBack: () -> Unit,
    onShowBadgePreview: (String?, String) -> Unit,
    viewModel: ClanDetailViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(clanTag) {
        viewModel.submitIntent(Intent.Load(clanTag))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is Effect.ShowClanBadgePreview -> onShowBadgePreview(effect.imageUrl, effect.clanName)
            }
        }
    }

    ClanDetailScreen(
        state = state,
        onNavigateBack = onNavigateBack,
        onBadgeClick = { viewModel.submitIntent(Intent.BadgeClicked) },
        onRetry = { viewModel.submitIntent(Intent.Load(clanTag)) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClanDetailScreen(
    state: ClanDetailContract.State,
    onNavigateBack: () -> Unit,
    onBadgeClick: () -> Unit,
    onRetry: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.clan?.name ?: stringResource(R.string.clan_detail_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.clan_detail_back))
                    }
                },
            )
        },
    ) { paddingValues ->
        when {
            state.isLoading -> FullScreenLoading(modifier = Modifier.padding(paddingValues))
            state.errorMessage != null -> FullScreenError(
                message = state.errorMessage,
                onRetry = onRetry,
                modifier = Modifier.padding(paddingValues),
            )

            state.clan != null -> ClanDetailContent(
                clan = state.clan,
                onBadgeClick = onBadgeClick,
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}

@Composable
private fun ClanDetailContent(
    clan: Clan,
    onBadgeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircleNetworkImage(
            url = clan.badgeUrls.medium,
            contentDescription = clan.name,
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
                .clickable(onClick = onBadgeClick),
        )

        Text(text = clan.name, style = MaterialTheme.typography.titleLarge)
        Text(text = clan.tag, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(24.dp))

        DetailRow(stringResource(R.string.clan_members_count, clan.members))
        DetailRow(stringResource(R.string.clan_points, clan.clanPoints))
        DetailRow(stringResource(R.string.clan_war_wins, clan.warWins))
        DetailRow(stringResource(R.string.clan_war_losses, clan.warLosses))
    }
}

@Composable
private fun DetailRow(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
    )
}
