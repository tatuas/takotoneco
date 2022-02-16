package com.tatuas.takotoneco.usecase

import com.tatuas.takotoneco.data.api.structure.UserDetailResponse
import com.tatuas.takotoneco.data.logger.ErrorLogger
import com.tatuas.takotoneco.model.LoadState
import com.tatuas.takotoneco.model.UserItem
import com.tatuas.takotoneco.repogitory.GitHubRepository
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import java.util.*

@ExperimentalCoroutinesApi
class UserDetailUseCaseTest : ShouldSpec({
    val gitHubRepository: GitHubRepository = mockk {
        coEvery { getUserDetail("login") } returns UserDetailResponse(
            0,
            "",
            "",
            "",
            0,
            0,
            0,
            0,
            Date()
        )
    }

    val dispatcher = UnconfinedTestDispatcher()

    val errorLogger: ErrorLogger = mockk()

    should("The first value is Loading, and the number of values flowing in is 2.") {
        val useCase = UserDetailUseCase(dispatcher, gitHubRepository, errorLogger)

        val data: UserItem.Data = mockk {
            every { login } returns "login"
        }

        useCase.getDetail(data).first() shouldBe LoadState.Loading
        useCase.getDetail(data).count() shouldBe 2
    }
})

