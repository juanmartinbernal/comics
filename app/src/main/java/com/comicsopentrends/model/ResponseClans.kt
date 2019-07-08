package com.comicsopentrends.model

class ResponseClans {
    var paging: Paging? = null
    var items: List<ItemsItem>? = null

    override fun toString(): String {
        return "ResponseClans{" +
                "paging = '" + paging + '\''.toString() +
                ",items = '" + items + '\''.toString() +
                "}"
    }
}