package com.kopa.me.driver.core

import android.app.Application
import com.kopa.me.driver.core.di.DataSourceLocalModule
import com.kopa.me.driver.core.di.DataSourceRemoteModule
import com.kopa.me.driver.core.di.DatabaseModule
import com.kopa.me.driver.core.di.NetworkModule
import com.kopa.me.driver.core.di.RepositoryModule
import com.kopa.me.driver.core.di.ViewModelsModule
import com.kopa.me.driver.core.utils.DebugConfig
import com.kopa.me.driver.core.utils.MyLogger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Contains dependency modules
 * From top to bottom representing DI tree
 * For example: [ViewModelsModule] depends on [RepositoryModule]
 * [RepositoryModule] depends on [DataSourceLocalModule] and [DataSourceRemoteModule]
 * and so on.
 */

class MedriverApp : Application() {

	companion object {
		@Volatile
		internal var debug: DebugConfig = DebugConfig.Default

		/**
		 * Enable or disable [Application] debug mode.
		 * enabled by default
		 *
		 * @param enabled enable the debug mode.
		 * @param logger logging implementation.
		 */
		fun debugMode(enabled: Boolean, logger: MyLogger) {
			debug = object: DebugConfig {
				override val enabled = enabled
				override val logger = logger
			}
		}
	}

	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidContext(this@MedriverApp)
			if (debug.enabled) {
				androidLogger()
			}
			koin.loadModules(
				listOf(
					ViewModelsModule,
					RepositoryModule,
					DataSourceRemoteModule, DataSourceLocalModule,
					NetworkModule, DatabaseModule
				)
			)
			koin.createRootScope()
		}
	}
}