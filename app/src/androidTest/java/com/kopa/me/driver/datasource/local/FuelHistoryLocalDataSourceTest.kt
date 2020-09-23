package com.kopa.me.driver.datasource.local

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kopa.me.driver.core.utils.DateConverter
import com.kopa.me.driver.data.datasource.local.user.history.IUserHistoryLocalDataSource
import com.kopa.me.driver.data.datasource.local.user.history.entities.UserHistoryEntity
import com.kopa.me.driver.domain.core.ResultState
import com.kopa.me.driver.modules.DatabaseTestModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

/**
 * Tests for [IUserHistoryLocalDataSource]
 */

@RunWith(AndroidJUnit4::class)
class UserHistoryLocalDataSourceTest : KoinTest {


    private val dataSource: IUserHistoryLocalDataSource by inject()

    private lateinit var UserHistoryEntity1: UserHistoryEntity
    private lateinit var UserHistoryEntity2: UserHistoryEntity
    private lateinit var UserHistoryEntity3: UserHistoryEntity


    /**
     * Override test module
     */
    @Before
    fun before() {
        loadKoinModules(DatabaseTestModule)

        //dataSource = UserHistoryLocalDataSourceImpl(dao)

        UserHistoryEntity1 = UserHistoryEntity(
			historyEntryId = 1,
			commentary = "",
			timestamp = DateConverter.toDate("10-01-2020").time
		)
        UserHistoryEntity2 = UserHistoryEntity(
			historyEntryId = 2,
			commentary = "",
			timestamp = DateConverter.toDate("11-01-2020").time
		)
        UserHistoryEntity3 = UserHistoryEntity(
			historyEntryId = 3,
			commentary = "",
			timestamp = DateConverter.toDate("12-01-2020").time
		)
    }


    @Test
    fun testInsertUserHistoryEntity() = runBlocking {
        dataSource.insertUserHistoryEntry(UserHistoryEntity1)
        dataSource.insertUserHistoryEntry(UserHistoryEntity2)
        dataSource.insertUserHistoryEntry(UserHistoryEntity3)

        // Request
        val result = dataSource.getUserHistory(1, 0)

        // compare result
        Assert.assertTrue(result is ResultState.Success)
        result.fold(
			success = { Assert.assertEquals(it, listOf(UserHistoryEntity3)) },
			failure = {}
		)

    }


    @Test
    fun testDeleteUserHistoryEntity() = runBlocking {
        // Request
        val result = dataSource.getUserHistory(1, 0)

        // compare result
        Assert.assertTrue(result is ResultState.Success)
        result.fold(
			success = { Assert.assertEquals(it, listOf(UserHistoryEntity3)) },
			failure = {}
		)
        delay(50)

        val deletionResult = dataSource.deleteUserHistoryEntry(UserHistoryEntity1)
        Assert.assertTrue(deletionResult is ResultState.Success)

        val afterDeletionResult = dataSource.getUserHistory(10, 0)
        Assert.assertTrue(deletionResult is ResultState.Success)

        afterDeletionResult.fold(
			success = {
				Assert.assertTrue(it.isNotEmpty())
				Log.wtf("mylogs", "${it.first().historyEntryId}")
				Assert.assertTrue(it.size != 3)
			},
			failure = {}
		)

    }

}