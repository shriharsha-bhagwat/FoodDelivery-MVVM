package com.kopa.me.driver.domain.user.history

import com.kopa.me.driver.domain.core.SimpleResult
import com.kopa.me.driver.domain.user.history.model.UserHistoryRecord

/**
 * User History repository provides data for [com.kopa.me.driver.presentation.ui.user.history]
 */

interface IUserHistoryRepository {

    /**
     * @param size defines how many entries should be loaded to display in UI
     */
    suspend fun loadUserHistory(size: Int?): SimpleResult<List<UserHistoryRecord>>

    suspend fun addUserHistoryRecord(fuelHistoryRecord: UserHistoryRecord): SimpleResult<Unit>

    suspend fun removeUserHistoryRecord(fuelHistoryRecord: UserHistoryRecord): SimpleResult<Unit>

}