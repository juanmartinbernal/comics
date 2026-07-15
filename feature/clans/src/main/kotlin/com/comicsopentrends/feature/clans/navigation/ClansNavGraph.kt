package com.comicsopentrends.feature.clans.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.comicsopentrends.core.ui.components.CircleNetworkImage
import com.comicsopentrends.feature.clans.detail.ClanDetailRoute
import com.comicsopentrends.feature.clans.list.ClanListRoute

const val CLANS_GRAPH_ROUTE = "clans_graph"
const val CLAN_LIST_ROUTE = "clan_list"
private const val CLAN_DETAIL_ROUTE = "clan_detail"
private const val CLAN_TAG_ARG = "clanTag"

private data class BadgePreview(val imageUrl: String?, val clanName: String)

fun NavGraphBuilder.clansNavGraph(navController: NavHostController) {
    navigation(startDestination = CLAN_LIST_ROUTE, route = CLANS_GRAPH_ROUTE) {
        composable(CLAN_LIST_ROUTE) {
            var badgePreview by remember { mutableStateOf<BadgePreview?>(null) }

            ClanListRoute(
                onNavigateToDetail = { clanTag ->
                    navController.navigate("$CLAN_DETAIL_ROUTE/${clanTag.encodeForRoute()}")
                },
                onShowBadgePreview = { url, name -> badgePreview = BadgePreview(url, name) },
            )

            badgePreview?.let { preview ->
                BadgePreviewDialog(preview = preview, onDismiss = { badgePreview = null })
            }
        }

        composable(
            route = "$CLAN_DETAIL_ROUTE/{$CLAN_TAG_ARG}",
            arguments = listOf(navArgument(CLAN_TAG_ARG) { type = NavType.StringType }),
        ) { backStackEntry ->
            var badgePreview by remember { mutableStateOf<BadgePreview?>(null) }
            val clanTag = backStackEntry.arguments?.getString(CLAN_TAG_ARG).orEmpty().decodeFromRoute()

            ClanDetailRoute(
                clanTag = clanTag,
                onNavigateBack = { navController.popBackStack() },
                onShowBadgePreview = { url, name -> badgePreview = BadgePreview(url, name) },
            )

            badgePreview?.let { preview ->
                BadgePreviewDialog(preview = preview, onDismiss = { badgePreview = null })
            }
        }
    }
}

@Composable
private fun BadgePreviewDialog(preview: BadgePreview, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (preview.imageUrl.isNullOrBlank()) {
                CircularProgressIndicator()
            } else {
                CircleNetworkImage(
                    url = preview.imageUrl,
                    contentDescription = preview.clanName,
                    modifier = Modifier.size(220.dp),
                )
            }
        }
    }
}

// Los tags de clan empiezan por '#', que hay que escapar para poder usarlos como argumento de ruta.
private fun String.encodeForRoute(): String = replace("#", "%23")
private fun String.decodeFromRoute(): String = replace("%23", "#")
