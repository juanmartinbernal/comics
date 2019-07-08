package com.comicsopentrends.model

class ItemsItem {
    var warFrequency: String? = null
    var warWins: Int = 0
    var warWinStreak: Int = 0
    var clanLevel: Int = 0
    var requiredTrophies: Int = 0
    var badgeUrls: BadgeUrls? = null
    var isIsWarLogPublic: Boolean = false
    var type: String? = null
    var clanPoints: Int = 0
    var warTies: Int = 0
    var warLosses: Int = 0
    var clanVersusPoints: Int = 0
    var members: Int = 0
    var name: String? = null
    var location: Location? = null
    var tag: String? = null

    override fun toString(): String {
        return "ItemsItem{" +
                "warFrequency = '" + warFrequency + '\''.toString() +
                ",warWins = '" + warWins + '\''.toString() +
                ",warWinStreak = '" + warWinStreak + '\''.toString() +
                ",clanLevel = '" + clanLevel + '\''.toString() +
                ",requiredTrophies = '" + requiredTrophies + '\''.toString() +
                ",badgeUrls = '" + badgeUrls + '\''.toString() +
                ",isWarLogPublic = '" + isIsWarLogPublic + '\''.toString() +
                ",type = '" + type + '\''.toString() +
                ",clanPoints = '" + clanPoints + '\''.toString() +
                ",warTies = '" + warTies + '\''.toString() +
                ",warLosses = '" + warLosses + '\''.toString() +
                ",clanVersusPoints = '" + clanVersusPoints + '\''.toString() +
                ",members = '" + members + '\''.toString() +
                ",name = '" + name + '\''.toString() +
                ",location = '" + location + '\''.toString() +
                ",tag = '" + tag + '\''.toString() +
                "}"
    }
}
