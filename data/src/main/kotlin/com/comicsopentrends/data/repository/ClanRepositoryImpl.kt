package com.comicsopentrends.data.repository

import com.comicsopentrends.core.common.AppResult
import com.comicsopentrends.core.common.DispatcherProvider
import com.comicsopentrends.core.common.runCatchingResult
import com.comicsopentrends.data.mapper.toDomain
import com.comicsopentrends.data.remote.ClanApiService
import com.comicsopentrends.domain.model.Clan
import com.comicsopentrends.domain.model.ClanPage
import com.comicsopentrends.domain.repository.ClanRepository
import kotlinx.coroutines.withContext

class ClanRepositoryImpl(
    private val api: ClanApiService,
    private val dispatchers: DispatcherProvider,
) : ClanRepository {

    override suspend fun getClans(afterCursor: String?): AppResult<ClanPage> = withContext(dispatchers.io) {
        runCatchingResult {
            api.getClans(
                limit = ClanApiService.DEFAULT_PAGE_SIZE,
                warFrequency = ClanApiService.DEFAULT_WAR_FREQUENCY,
                after = afterCursor,
            ).toDomain()
        }
    }

    override suspend fun searchClans(name: String): AppResult<ClanPage> = withContext(dispatchers.io) {
        runCatchingResult { api.searchClans(name).toDomain() }
    }

    override suspend fun getClanDetail(tag: String): AppResult<Clan> = withContext(dispatchers.io) {
        runCatchingResult { api.getClanDetails(tag.toUrlSafeTag()).toDomain() }
    }

    /** Los tags de clan empiezan por '#', que debe ir codificado en la ruta de la URL. */
    private fun String.toUrlSafeTag(): String = replace("#", "%23")
}
