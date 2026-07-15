package com.comicsopentrends.domain.model

/** Representa un clan de Clash of Clans, tal y como lo consume la capa de presentación. */
data class Clan(
    val tag: String,
    val name: String,
    val type: ClanType,
    val clanLevel: Int,
    val clanPoints: Int,
    val members: Int,
    val warWins: Int,
    val warLosses: Int,
    val warTies: Int,
    val warWinStreak: Int,
    val warFrequency: String,
    val isWarLogPublic: Boolean,
    val location: ClanLocation?,
    val badgeUrls: ClanBadgeUrls,
)

enum class ClanType {
    OPEN,
    INVITE_ONLY,
    CLOSED,
    UNKNOWN,
}

data class ClanLocation(
    val id: Int,
    val name: String,
    val isCountry: Boolean,
    val countryCode: String?,
)

data class ClanBadgeUrls(
    val small: String?,
    val medium: String?,
    val large: String?,
)
