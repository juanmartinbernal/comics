package com.comicsopentrends.model

class Location {
    var isIsCountry: Boolean = false
    var countryCode: String? = null
    var name: String? = null
    var id: Int = 0

    override fun toString(): String {
        return "Location{" +
                "isCountry = '" + isIsCountry + '\''.toString() +
                ",countryCode = '" + countryCode + '\''.toString() +
                ",name = '" + name + '\''.toString() +
                ",id = '" + id + '\''.toString() +
                "}"
    }
}
