package com.comicsopentrends.data.remote.dto

data class BadgeUrlsDto(
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null,
)

data class LocationDto(
    val id: Int = 0,
    val name: String? = null,
    val isCountry: Boolean = false,
    val countryCode: String? = null,
)

data class ClanDto(
    val tag: String? = null,
    val name: String? = null,
    val type: String? = null,
    val clanLevel: Int = 0,
    val clanPoints: Int = 0,
    val members: Int = 0,
    val warWins: Int = 0,
    val warLosses: Int = 0,
    val warTies: Int = 0,
    val warWinStreak: Int = 0,
    val warFrequency: String? = null,
    val isWarLogPublic: Boolean = false,
    val location: LocationDto? = null,
    val badgeUrls: BadgeUrlsDto? = null,
)

data class CursorsDto(
    val after: String? = null,
    val before: String? = null,
)

data class PagingDto(
    val cursors: CursorsDto? = null,
)

data class ClanListResponseDto(
    val items: List<ClanDto> = emptyList(),
    val paging: PagingDto? = null,
)
