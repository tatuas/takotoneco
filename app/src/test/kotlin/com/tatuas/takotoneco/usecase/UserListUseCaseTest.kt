package com.tatuas.takotoneco.usecase

import com.tatuas.takotoneco.data.logger.ErrorLogger
import com.tatuas.takotoneco.model.LoadState
import com.tatuas.takotoneco.repogitory.GitHubRepository
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
class UserListUseCaseTest : ShouldSpec({
    val gitHubRepository: GitHubRepository = mockk {
        coEvery { getUserList(null) } returns emptyList()
    }

    val dispatcher = UnconfinedTestDispatcher()

    val errorLogger: ErrorLogger = mockk()

    should("The first value is Loading, and the number of values flowing in is 2.") {
        val useCase = UserListUseCase(dispatcher, gitHubRepository, errorLogger)

        useCase.getUserItemList().first() shouldBe LoadState.Loading
        useCase.getUserItemList().count() shouldBe 2
    }
})

