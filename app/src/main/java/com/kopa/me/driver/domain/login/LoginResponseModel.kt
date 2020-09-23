package com.kopa.me.driver.domain.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseModel(
    @SerialName("statusCode")
    val statusCode: Int = 0,
    @SerialName("data")
    val data: Data,
    @SerialName("message")
    val message: String = "",
    @SerialName("status")
    val status: Boolean = false
)

@Serializable
data class Data(
    @SerialName("driver_id")
    val driverId: Long = -1L,
    @SerialName("api_token")
    val apiToken: String = ""
)
