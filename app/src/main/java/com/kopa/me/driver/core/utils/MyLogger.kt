package com.kopa.me.driver.core.utils

import android.util.Log
import com.kopa.me.driver.core.utils.MyLogger.Default

/**
 * May be used to create a custom logging solution to override the [Default] behaviour.
 */
interface MyLogger {

	/**
	 * @param tag used to identify the source of a log message.
	 * @param message the message to be logged.
	 */
	fun logWarn(tag: String, message: String)

	fun logError(tag: String, message: String)

	fun logDebug(tag: String, message: String)

	fun logInfo(tag: String, message: String)

	fun logWtf(tag: String, message: String)

	/**
	 * Debug implementation of [MyLogger].
	 */
	object Debug : MyLogger {

		override fun logWarn(tag: String, message: String) {
			Log.w(tag, message)
		}

		override fun logError(tag: String, message: String) {
			Log.e(tag, message)
		}

		override fun logDebug(tag: String, message: String) {
			Log.d(tag, message)
		}

		override fun logInfo(tag: String, message: String) {
			Log.i(tag, message)
		}

		override fun logWtf(tag: String, message: String) {
			Log.wtf(tag, message)
		}
	}

	/**
	 * Default implementation of [MyLogger].
	 * No messages to Logcat
	 */
	object Default : MyLogger {
		override fun logWarn(tag: String, message: String) {}
		override fun logError(tag: String, message: String) {}
		override fun logDebug(tag: String, message: String) {}
		override fun logInfo(tag: String, message: String) {}
		override fun logWtf(tag: String, message: String) {}
	}
}