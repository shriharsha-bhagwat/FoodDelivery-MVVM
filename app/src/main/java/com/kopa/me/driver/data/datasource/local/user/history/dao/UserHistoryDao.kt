package com.kopa.me.driver.data.datasource.local.user.history.dao

import androidx.room.*
import com.kopa.me.driver.data.datasource.local.user.history.entities.UserHistoryEntity

/**
 * Dao interface to "talk" with RoomDatabase related to [HistoryLocalDataSource]
 */

@Dao
interface UserHistoryDao {

    @Query("SELECT * FROM user_history ORDER BY timestamp DESC LIMIT :limit OFFSET :offset")
    suspend fun getUserHistory(limit: Int, offset: Int): List<UserHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUserHistoryEntity(fuelHistoryEntity: UserHistoryEntity)

    @Delete
    suspend fun deleteUserHistoryEntity(fuelHistoryEntity: UserHistoryEntity)

    @Query("DELETE FROM user_history")
    suspend fun clearUserHistory()

}