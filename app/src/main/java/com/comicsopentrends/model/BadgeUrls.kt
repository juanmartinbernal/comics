package com.comicsopentrends.model

class BadgeUrls {
    var small: String? = null
    var large: String? = null
    var medium: String? = null

    override fun toString(): String {
        return "BadgeUrls{" +
                "small = '" + small + '\''.toString() +
                ",large = '" + large + '\''.toString() +
                ",medium = '" + medium + '\''.toString() +
                "}"
    }
}
