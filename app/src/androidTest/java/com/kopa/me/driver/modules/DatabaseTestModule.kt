package com.kopa.me.driver.modules

import androidx.room.Room
import com.kopa.me.driver.data.datasource.local.database.KopaRoomDatabase
import org.koin.dsl.module

/**
 * In-Memory Room Database definition
 */

val DatabaseTestModule = module (override = true) {
	
	single {
		// In-Memory database config
		Room.inMemoryDatabaseBuilder(get(), KopaRoomDatabase::class.java)
			.allowMainThreadQueries()
			.build()
	}
}
