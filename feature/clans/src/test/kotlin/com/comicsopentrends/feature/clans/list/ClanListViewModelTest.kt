package com.comicsopentrends.feature.clans.list

import app.cash.turbine.test
import com.comicsopentrends.core.common.AppResult
import com.comicsopentrends.domain.model.Clan
import com.comicsopentrends.domain.model.ClanBadgeUrls
import com.comicsopentrends.domain.model.ClanPage
import com.comicsopentrends.domain.model.ClanType
import com.comicsopentrends.domain.usecase.GetClansUseCase
import com.comicsopentrends.domain.usecase.SearchClansUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ClanListViewModelTest {

    private val getClansUseCase = mockk<GetClansUseCase>()
    private val searchClansUseCase = mockk<SearchClansUseCase>()
    private val dispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loads the first page of clans on start`() = runTest {
        val page = ClanPage(clans = listOf(sampleClan()), nextCursor = "cursor-1")
        coEvery { getClansUseCase(null) } returns AppResult.Success(page)

        val viewModel = ClanListViewModel(getClansUseCase, searchClansUseCase)

        viewModel.state.test {
            val loaded = awaitItemMatching { it.clans.isNotEmpty() }
            assertEquals(1, loaded.clans.size)
            assertEquals("cursor-1", loaded.nextCursor)
            assertTrue(loaded.canLoadMore)
        }
    }

    @Test
    fun `search intent with a short query falls back to the first page`() = runTest {
        val page = ClanPage(clans = listOf(sampleClan()), nextCursor = null)
        coEvery { getClansUseCase(null) } returns AppResult.Success(page)

        val viewModel = ClanListViewModel(getClansUseCase, searchClansUseCase)
        viewModel.submitIntent(ClanListContract.Intent.Search("a"))

        viewModel.state.test {
            val loaded = awaitItemMatching { it.query == "a" }
            assertEquals("a", loaded.query)
        }
    }

    private suspend fun app.cash.turbine.TurbineTestContext<ClanListContract.State>.awaitItemMatching(
        predicate: (ClanListContract.State) -> Boolean,
    ): ClanListContract.State {
        var state = awaitItem()
        while (!predicate(state)) {
            state = awaitItem()
        }
        return state
    }

    private fun sampleClan() = Clan(
        tag = "#ABC",
        name = "Verse",
        type = ClanType.OPEN,
        clanLevel = 10,
        clanPoints = 1000,
        members = 42,
        warWins = 5,
        warLosses = 1,
        warTies = 0,
        warWinStreak = 2,
        warFrequency = "always",
        isWarLogPublic = true,
        location = null,
        badgeUrls = ClanBadgeUrls(small = null, medium = null, large = null),
    )
}
