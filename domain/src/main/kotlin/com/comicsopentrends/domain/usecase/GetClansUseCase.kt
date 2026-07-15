package com.comicsopentrends.domain.usecase

import com.comicsopentrends.core.common.AppResult
import com.comicsopentrends.domain.model.ClanPage
import com.comicsopentrends.domain.repository.ClanRepository

/** Obtiene una página de clanes, opcionalmente a partir de un cursor de paginación. */
class GetClansUseCase(private val repository: ClanRepository) {
    suspend operator fun invoke(afterCursor: String? = null): AppResult<ClanPage> =
        repository.getClans(afterCursor)
}
