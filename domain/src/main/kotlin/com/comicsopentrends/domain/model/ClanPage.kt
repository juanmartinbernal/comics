package com.comicsopentrends.domain.model

/** Página de resultados de clanes, con el cursor necesario para pedir la siguiente página. */
data class ClanPage(
    val clans: List<Clan>,
    val nextCursor: String?,
)
