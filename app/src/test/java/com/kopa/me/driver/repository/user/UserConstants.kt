package com.kopa.me.driver.repository.user

import com.kopa.me.driver.data.datasource.remote.user.model.NetworkUserModelResponse
import com.kopa.me.driver.domain.user.UserType
import com.kopa.me.driver.readJson
import kotlinx.serialization.json.Json

/**
 *
 */

object UserConstants {

    private val responseList = listOf(
		readJson("OWNER"),
		readJson("DRIVER"),
		readJson("ADMIN"),
		readJson("CLIENT")
	)


    val networkResponse: Map<UserType, NetworkUserModelResponse> =
        UserType.values().zip(
			responseList.map {
				Json { ignoreUnknownKeys = true }
					.decodeFromString(NetworkUserModelResponse.serializer(), it)
			}
		).toMap()


}