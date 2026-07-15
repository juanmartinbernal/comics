package com.comicsopentrends.feature.clans.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.comicsopentrends.core.ui.components.CircleNetworkImage
import com.comicsopentrends.domain.model.Clan
import com.comicsopentrends.feature.clans.R

@Composable
fun ClanListItem(
    clan: Clan,
    onClick: () -> Unit,
    onBadgeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircleNetworkImage(
            url = clan.badgeUrls.small,
            contentDescription = clan.name,
            modifier = Modifier
                .size(56.dp)
                .clickable(onClick = onBadgeClick),
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = clan.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = stringResource(R.string.clan_members_count, clan.members),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
