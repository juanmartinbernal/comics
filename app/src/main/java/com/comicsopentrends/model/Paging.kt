package com.comicsopentrends.model

class Paging {
    var cursors: Cursors? = null

    override fun toString(): String {
        return "Paging{" +
                "cursors = '" + cursors + '\''.toString() +
                "}"
    }
}
