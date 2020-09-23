package com.kopa.me.driver.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Dispatchers wrapper class
 */

object MyDispatchers {

	fun io(): CoroutineDispatcher = Dispatchers.IO
	fun default(): CoroutineDispatcher = Dispatchers.Default
	fun main(): CoroutineDispatcher = Dispatchers.Main
	fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined

}