package com.comicsopentrends.core.network

/**
 * Configuración necesaria para inicializar el cliente HTTP de la Clash of Clans API.
 * La instancia real (con el token) se provee desde el módulo `:app` a partir de un
 * BuildConfig field, para no versionar secretos en el código fuente.
 */
data class ClanApiConfig(
    val apiToken: String,
    val baseUrl: String = "https://api.clashofclans.com/v1/",
    val enableHttpLogging: Boolean = false,
)
