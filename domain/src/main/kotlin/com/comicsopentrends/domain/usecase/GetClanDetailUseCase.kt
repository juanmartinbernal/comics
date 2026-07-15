package com.comicsopentrends.domain.usecase

import com.comicsopentrends.core.common.AppResult
import com.comicsopentrends.domain.model.Clan
import com.comicsopentrends.domain.repository.ClanRepository

/** Obtiene el detalle completo de un clan a partir de su tag. */
class GetClanDetailUseCase(private val repository: ClanRepository) {
    suspend operator fun invoke(tag: String): AppResult<Clan> = repository.getClanDetail(tag)
}
