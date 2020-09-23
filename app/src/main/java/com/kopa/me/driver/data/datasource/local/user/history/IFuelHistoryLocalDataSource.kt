package com.kopa.me.driver.data.datasource.local.user.history

import com.kopa.me.driver.data.datasource.local.user.history.entities.UserHistoryEntity
import com.kopa.me.driver.domain.core.SimpleResult

/**
 * User History DataSource which works with local database
 */

interface IUserHistoryLocalDataSource {

    suspend fun getUserHistory(limit: Int, offset: Int): SimpleResult<List<UserHistoryEntity>>
    suspend fun insertUserHistoryEntry(userHistoryEntity: UserHistoryEntity): SimpleResult<Unit>
    suspend fun deleteUserHistoryEntry(userHistoryEntity: UserHistoryEntity): SimpleResult<Unit>

}