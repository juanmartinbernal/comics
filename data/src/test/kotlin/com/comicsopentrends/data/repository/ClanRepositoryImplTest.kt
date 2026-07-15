package com.comicsopentrends.data.repository

import com.comicsopentrends.core.common.AppResult
import com.comicsopentrends.core.common.DispatcherProvider
import com.comicsopentrends.data.remote.ClanApiService
import com.comicsopentrends.data.remote.dto.BadgeUrlsDto
import com.comicsopentrends.data.remote.dto.ClanDto
import com.comicsopentrends.data.remote.dto.ClanListResponseDto
import com.comicsopentrends.data.remote.dto.CursorsDto
import com.comicsopentrends.data.remote.dto.PagingDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ClanRepositoryImplTest {

    private val api = mockk<ClanApiService>()
    private val testDispatcher = StandardTestDispatcher()
    private val dispatchers = object : DispatcherProvider {
        override val main = testDispatcher
        override val io = testDispatcher
        override val default = testDispatcher
    }
    private val repository = ClanRepositoryImpl(api, dispatchers)

    @Test
    fun `getClans maps the response and exposes the next cursor`() = runTest(testDispatcher) {
        val dto = ClanListResponseDto(
            items = listOf(sampleClanDto(tag = "#ABC")),
            paging = PagingDto(CursorsDto(after = "next-cursor")),
        )
        coEvery { api.getClans(any(), any(), any()) } returns dto

        val result = repository.getClans(afterCursor = null)

        assertTrue(result is AppResult.Success)
        val page = (result as AppResult.Success).data
        assertEquals(1, page.clans.size)
        assertEquals("#ABC", page.clans.first().tag)
        assertEquals("next-cursor", page.nextCursor)
    }

    @Test
    fun `getClanDetail url-encodes the clan tag before calling the api`() = runTest(testDispatcher) {
        coEvery { api.getClanDetails(any()) } returns sampleClanDto(tag = "#2Y0QRQCC")

        val result = repository.getClanDetail("#2Y0QRQCC")

        assertTrue(result is AppResult.Success)
        coVerify { api.getClanDetails("%232Y0QRQCC") }
    }

    @Test
    fun `getClanDetail returns an Error result when the api call fails`() = runTest(testDispatcher) {
        val exception = IllegalStateException("boom")
        coEvery { api.getClanDetails(any()) } throws exception

        val result = repository.getClanDetail("#ABC")

        assertTrue(result is AppResult.Error)
        assertEquals(exception, (result as AppResult.Error).cause)
    }

    private fun sampleClanDto(tag: String) = ClanDto(
        tag = tag,
        name = "Verse",
        type = "open",
        members = 42,
        badgeUrls = BadgeUrlsDto(small = "small.png"),
    )
}
