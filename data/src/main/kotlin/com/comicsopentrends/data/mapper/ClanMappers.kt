package com.comicsopentrends.data.mapper

import com.comicsopentrends.data.remote.dto.BadgeUrlsDto
import com.comicsopentrends.data.remote.dto.ClanDto
import com.comicsopentrends.data.remote.dto.ClanListResponseDto
import com.comicsopentrends.data.remote.dto.LocationDto
import com.comicsopentrends.domain.model.Clan
import com.comicsopentrends.domain.model.ClanBadgeUrls
import com.comicsopentrends.domain.model.ClanLocation
import com.comicsopentrends.domain.model.ClanPage
import com.comicsopentrends.domain.model.ClanType

fun ClanDto.toDomain(): Clan = Clan(
    tag = tag.orEmpty(),
    name = name.orEmpty(),
    type = type.toClanType(),
    clanLevel = clanLevel,
    clanPoints = clanPoints,
    members = members,
    warWins = warWins,
    warLosses = warLosses,
    warTies = warTies,
    warWinStreak = warWinStreak,
    warFrequency = warFrequency.orEmpty(),
    isWarLogPublic = isWarLogPublic,
    location = location?.toDomain(),
    badgeUrls = badgeUrls.toDomain(),
)

fun ClanListResponseDto.toDomain(): ClanPage = ClanPage(
    clans = items.map { it.toDomain() },
    nextCursor = paging?.cursors?.after?.takeIf { it.isNotBlank() },
)

private fun LocationDto.toDomain(): ClanLocation = ClanLocation(
    id = id,
    name = name.orEmpty(),
    isCountry = isCountry,
    countryCode = countryCode,
)

private fun BadgeUrlsDto?.toDomain(): ClanBadgeUrls = ClanBadgeUrls(
    small = this?.small,
    medium = this?.medium,
    large = this?.large,
)

private fun String?.toClanType(): ClanType = when (this) {
    "open" -> ClanType.OPEN
    "inviteOnly" -> ClanType.INVITE_ONLY
    "closed" -> ClanType.CLOSED
    else -> ClanType.UNKNOWN
}
