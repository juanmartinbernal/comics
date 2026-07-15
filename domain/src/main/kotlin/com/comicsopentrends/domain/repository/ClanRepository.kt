package com.comicsopentrends.domain.repository

import com.comicsopentrends.core.common.AppResult
import com.comicsopentrends.domain.model.Clan
import com.comicsopentrends.domain.model.ClanPage

interface ClanRepository {

    /** Lista paginada de clanes, ordenados por la API. [afterCursor] pide la página siguiente. */
    suspend fun getClans(afterCursor: String? = null): AppResult<ClanPage>

    /** Busca clanes cuyo nombre empiece por [name]. */
    suspend fun searchClans(name: String): AppResult<ClanPage>

    /** Detalle completo de un clan dado su [tag] (ej. "#2Y0QRQCC"). */
    suspend fun getClanDetail(tag: String): AppResult<Clan>
}
