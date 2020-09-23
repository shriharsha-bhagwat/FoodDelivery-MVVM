package com.kopa.me.driver.data.core.base

import com.kopa.me.driver.core.utils.logDebug
import com.kopa.me.driver.domain.core.ResultState
import com.kopa.me.driver.domain.core.SimpleResult

open class BaseDataSource {

	protected val TAG = "mylogs_${javaClass.simpleName}"

	suspend fun <T> safeCall(call: suspend ()-> T) : SimpleResult<T> =
		try {
			ResultState.Success(call.invoke())
		}
		catch (t: Throwable) {
			logDebug(TAG, "${t.message}")
			ResultState.Failure(t)
		}
}