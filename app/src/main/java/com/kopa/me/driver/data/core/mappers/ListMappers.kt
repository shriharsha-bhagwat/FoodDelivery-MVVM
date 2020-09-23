package com.kopa.me.driver.data.core.mappers


// Non-nullable to Non-nullable
inline fun <I, O> mapList(input: List<I>, mapSingle: (I) -> O): List<O> {
	return input.map { mapSingle(it) }
}

// Nullable to Non-nullable
inline fun <I, O> mapNullInputList(input: List<I>?, mapSingle: (I) -> O): List<O> {
	return input?.map { mapSingle(it) } ?: emptyList()
}

// Non-nullable to Nullable
inline fun <I, O> mapNullOutputList(input: List<I>, mapSingle: (I) -> O): List<O>? {
	return if (input.isEmpty()) null else input.map { mapSingle(it) }
}

// Nullable to Non-nullable
inline fun <I, O> mapNullInputListToSet(input: List<I>?, mapSingle: (I) -> O): Set<O> {
	return input?.map { mapSingle(it) }?.toSet() ?: emptySet()
}