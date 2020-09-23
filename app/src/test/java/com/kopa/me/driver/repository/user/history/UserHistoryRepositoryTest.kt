package com.kopa.me.driver.repository.user.history

import com.kopa.me.driver.core.utils.DateConverter
import com.kopa.me.driver.data.datasource.local.user.history.IUserHistoryLocalDataSource
import com.kopa.me.driver.data.datasource.local.user.history.entities.UserHistoryEntity
import com.kopa.me.driver.data.repository.user.history.UserHistoryRepositoryImpl
import com.kopa.me.driver.data.repository.user.history.mappers.UserHistoryMappersFacade
import com.kopa.me.driver.domain.core.ResultState
import com.kopa.me.driver.domain.user.history.IUserHistoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for [IUserHistoryRepository]
 */

@RunWith(JUnit4::class)
class UserHistoryRepositoryTest {

    private val localDataSource: IUserHistoryLocalDataSource = mockk()
    private val mappers = UserHistoryMappersFacade()

    private val repository: IUserHistoryRepository =
        UserHistoryRepositoryImpl(localDataSource, mappers)

    private val validLimit = 20
    private val validOffset = 0
    private val invalidLimit = 1


    private val validReturn = listOf(
		UserHistoryEntity(
			historyEntryId = 1,
			commentary = "",
			timestamp = DateConverter.toDate("12-01-2020").time
		)
	)

    @Before
    fun before() {
        coEvery {
            localDataSource.getUserHistory(validLimit, validOffset)
        } returns ResultState.Success(validReturn)

        coEvery {
            localDataSource.getUserHistory(invalidLimit, validOffset)
        } returns ResultState.Failure(Exception("Invalid"))
    }

    @Test
    fun testSuccessfulReturnFromLocalDataSource() = runBlocking {

        val result = repository.loadUserHistory(null)

        result.fold(
			success = { Assert.assertTrue(it.isNotEmpty()) },
			failure = { }
		)

        Assert.assertTrue(result is ResultState.Success)

    }

    @Test
    fun testErrorReturnFromLocalDataSource() = runBlocking {
        Assert.assertTrue(repository.loadUserHistory(invalidLimit) is ResultState.Failure)
    }

}