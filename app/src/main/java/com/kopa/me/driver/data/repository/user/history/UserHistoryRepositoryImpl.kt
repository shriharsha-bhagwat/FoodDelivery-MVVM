package com.kopa.me.driver.data.repository.user.history

import com.kopa.me.driver.data.core.base.BaseRepository
import com.kopa.me.driver.data.datasource.local.user.history.IUserHistoryLocalDataSource
import com.kopa.me.driver.data.repository.user.history.mappers.UserHistoryMappersFacade
import com.kopa.me.driver.domain.core.ResultState
import com.kopa.me.driver.domain.core.SimpleResult
import com.kopa.me.driver.domain.user.history.IUserHistoryRepository
import com.kopa.me.driver.domain.user.history.model.UserHistoryRecord

/**
 * [IUserHistoryRepository] implementation
 */

internal class UserHistoryRepositoryImpl(
	//private val dataSourceRemote: RemoteDataSource,
	private val dataSourceLocal: IUserHistoryLocalDataSource,
	private val mappers: UserHistoryMappersFacade
) : BaseRepository(), IUserHistoryRepository {

    companion object {
        private const val startItemsCount = 20
        private const val startHistoryOffset = 0
    }

    //offset cursor position
    private var historyOffset = 0

    override suspend fun loadUserHistory(size: Int?): SimpleResult<List<UserHistoryRecord>> =
        if (size == null || size < 0) loadFirstUserHistory()
        else loadMoreUserHistory(size)

    private suspend fun loadFirstUserHistory(): SimpleResult<List<UserHistoryRecord>> =
        dataSourceLocal.getUserHistory(startItemsCount, startHistoryOffset).fold(
			success = { dto ->
				//reset offset
				historyOffset = 0
				ResultState.Success(mappers.mapDbHistoryToDm(dto)).also {
					//update offset after first items was loaded
					historyOffset += it.data.size
				}
			},
			failure = { throwable -> ResultState.Failure(throwable) }
		)

    private suspend fun loadMoreUserHistory(size: Int): SimpleResult<List<UserHistoryRecord>> =
        dataSourceLocal.getUserHistory(size, historyOffset).fold(
			success = { dto ->
				ResultState.Success(mappers.mapDbHistoryToDm(dto)).also {
					//update offset
					historyOffset += it.data.size
				}
			},
			failure = { throwable -> ResultState.Failure(throwable) }
		)

    override suspend fun addUserHistoryRecord(userHistoryRecord: UserHistoryRecord): SimpleResult<Unit> =
        dataSourceLocal.insertUserHistoryEntry(mappers.mapDmHistoryToDb(userHistoryRecord))

    override suspend fun removeUserHistoryRecord(userHistoryRecord: UserHistoryRecord): SimpleResult<Unit> =
        dataSourceLocal.deleteUserHistoryEntry(mappers.mapDmHistoryToDb(userHistoryRecord))
}