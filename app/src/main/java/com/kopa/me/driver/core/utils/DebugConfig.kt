package com.kopa.me.driver.core.utils

import com.kopa.me.driver.BuildConfig

internal interface DebugConfig {

	val enabled: Boolean
	val logger: MyLogger

	object Default : DebugConfig {
		override val enabled: Boolean = BuildConfig.DEBUG
		override val logger: MyLogger = if (enabled) MyLogger.Debug else MyLogger.Default
	}
}