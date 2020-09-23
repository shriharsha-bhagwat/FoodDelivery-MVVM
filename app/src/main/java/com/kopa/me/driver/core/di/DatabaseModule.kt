package com.kopa.me.driver.core.di

import android.app.Application
import androidx.room.Room
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.ironz.binaryprefs.Preferences
import com.kopa.me.driver.data.datasource.local.database.KopaRoomDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * [DatabaseModule] provides RoomDatabase, DAO instances, SharedPrefs
 */

private const val DATABASE_NAME = "kopa_database"
private const val PREFERENCES_NAME = "kopa_preferences"

val DatabaseModule = module {
	
	single { provideDatabase(androidApplication()) }
	/*single { provideUserHistoryDao(db = get()) }*/
	single { providePreferences(androidApplication()) }
}

private fun providePreferences(app: Application): Preferences {
	return BinaryPreferencesBuilder(app).name(PREFERENCES_NAME).build()
}

private fun provideDatabase(app: Application): KopaRoomDatabase {
	return Room
		.databaseBuilder(app, KopaRoomDatabase::class.java, DATABASE_NAME)
		.build()
}

/*private fun provideUserHistoryDao(db: KopaRoomDatabase): HistoryDao= db.getHistoryDao()*/
