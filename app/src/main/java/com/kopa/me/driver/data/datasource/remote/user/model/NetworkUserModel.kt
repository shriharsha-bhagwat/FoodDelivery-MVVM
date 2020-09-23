package com.kopa.me.driver.data.datasource.remote.user.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * network models which is using by [com.kopa.me.driver.data.datasource.remote.User.api.UserApi]
 * and [com.kopa.me.driver.data.datasource.remote.User.UserPricesRemoteDataSourceImpl]
 */

@Serializable
data class NetworkUserSummary(
	@SerialName("minval")
	val minPrice: String,
)


@Serializable
data class NetworkUserModel(
	@SerialName("total")
	val UserSummaryResponse: List<NetworkUserSummary> = emptyList()
)

@Serializable
data class NetworkUserModelResponse(val result: NetworkUserModel)




