package com.kopa.me.driver.core.utils

import com.ironz.binaryprefs.Preferences
import com.ironz.binaryprefs.PreferencesEditor
import com.kopa.me.driver.core.MedriverApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlin.math.round

/**
 *
 */


internal fun logWarn(tag: String = "mylogs", message: String) =
	MedriverApp.debug.logger.logWarn(tag, message)

internal fun logError(tag: String = "mylogs", message: String) =
	MedriverApp.debug.logger.logError(tag, message)

internal fun logDebug(tag: String = "mylogs", message: String) =
	MedriverApp.debug.logger.logDebug(tag, message)

internal fun logInfo(tag: String = "mylogs", message: String) =
	MedriverApp.debug.logger.logInfo(tag, message)

internal fun logWtf(tag: String = "mylogs", message: String) =
	MedriverApp.debug.logger.logWtf(tag, message)


// @see https://stackoverflow.com/questions/58658630/parallel-request-with-retrofit-coroutines-and-suspend-functions
// Flow.toMap()
suspend fun <K, V> Flow<Pair<K, V>>.toMap(): Map<K, V> {
	val result = mutableMapOf<K, V>()
	collect { (k, v) -> result[k] = v }
	return result
}


fun Float.roundTo(decimals: Int): Float {
	var multiplier = 1.0
	repeat(decimals) { multiplier *= 10 }
	return (round(this * multiplier) / multiplier).toFloat()
}

fun Double.roundTo(decimals: Int): Double {
	var multiplier = 1.0
	repeat(decimals) { multiplier *= 10 }
	return round(this * multiplier) / multiplier
}

fun Preferences.commit(block: PreferencesEditor.() -> Unit) {
	val editor = this.edit()
	block(editor)
	editor.commit()
}

fun Preferences.apply(block: PreferencesEditor.() -> Unit) {
	val editor = this.edit()
	block(editor)
	editor.apply()
}

