package com.kopa.me.driver.data.core.mappers

/**
 * used to map entities between layers
 */

internal interface IMapper<I, O> {
	
	fun map(input: I): O
	
}
