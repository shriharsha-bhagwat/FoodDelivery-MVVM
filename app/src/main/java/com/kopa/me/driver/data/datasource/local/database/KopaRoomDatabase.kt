package com.kopa.me.driver.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kopa.me.driver.data.datasource.local.user.history.dao.UserHistoryDao
import com.kopa.me.driver.data.datasource.local.user.history.entities.UserHistoryEntity

/**
 * Room Database
 */

@Database(
    entities = [
        UserHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class KopaRoomDatabase : RoomDatabase() {

    abstract fun getFuelHistoryDao(): UserHistoryDao

}