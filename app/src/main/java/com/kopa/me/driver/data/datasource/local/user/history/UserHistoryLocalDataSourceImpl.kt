package com.kopa.me.driver.data.datasource.local.user.history

import com.kopa.me.driver.core.utils.logDebug
import com.kopa.me.driver.data.core.base.BaseDataSource
import com.kopa.me.driver.data.datasource.local.user.history.dao.UserHistoryDao
import com.kopa.me.driver.data.datasource.local.user.history.entities.UserHistoryEntity
import com.kopa.me.driver.domain.core.SimpleResult

/**
 * [IUserHistoryLocalDataSource] implementation
 */

internal class UserHistoryLocalDataSourceImpl(private val dao: UserHistoryDao) :
    BaseDataSource(), IUserHistoryLocalDataSource {


    override suspend fun getUserHistory(
		limit: Int,
		offset: Int
	): SimpleResult<List<UserHistoryEntity>> =
        safeCall { dao.getUserHistory(limit, offset) }

    override suspend fun insertUserHistoryEntry(userHistoryEntity: UserHistoryEntity): SimpleResult<Unit> =
        safeCall { dao.insertUserHistoryEntity(userHistoryEntity) }.also {
            logDebug(TAG, "Adding History entry: id = ${userHistoryEntity.historyEntryId}")
        }

    override suspend fun deleteUserHistoryEntry(userHistoryEntity: UserHistoryEntity): SimpleResult<Unit> =
        safeCall { dao.deleteUserHistoryEntity(userHistoryEntity) }.also {
            logDebug(TAG, "Deleting History entry: id = ${userHistoryEntity.historyEntryId}")
        }

}