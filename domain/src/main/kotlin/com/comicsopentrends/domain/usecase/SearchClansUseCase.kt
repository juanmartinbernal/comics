package com.comicsopentrends.domain.usecase

import com.comicsopentrends.core.common.AppResult
import com.comicsopentrends.domain.model.ClanPage
import com.comicsopentrends.domain.repository.ClanRepository

/** Busca clanes cuyo nombre empiece por el texto indicado. */
class SearchClansUseCase(private val repository: ClanRepository) {
    suspend operator fun invoke(name: String): AppResult<ClanPage> = repository.searchClans(name)
}
