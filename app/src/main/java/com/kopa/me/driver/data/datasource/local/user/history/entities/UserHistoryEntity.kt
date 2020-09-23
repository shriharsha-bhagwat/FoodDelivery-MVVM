package com.kopa.me.driver.data.datasource.local.user.history.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Used in User History Local DataSource
 */

@Entity(tableName = "user_history")
data class UserHistoryEntity(

	@PrimaryKey(autoGenerate = true)
	var historyEntryId: Long,
	val commentary: String,
	val timestamp: Long

	/*@Embedded
    val another: AnotherEntity,*/
)