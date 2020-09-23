package com.kopa.me.driver.domain.core

/**
 * Primary used to define bounds between network and ui states
 */

typealias SimpleResult<T> = ResultState<T, Throwable>

sealed class ResultState<out T, out E> {

	data class Success<out T>(val data: T) : ResultState<T, Nothing>()

	data class Failure<out E>(val error: E) : ResultState<Nothing, E>()
	
	inline fun <C> fold(success: (T) -> C,
	                    failure: (E) -> C): C =
		when (this) {
			is Success -> success(data)
			is Failure -> failure(error)
		}

}