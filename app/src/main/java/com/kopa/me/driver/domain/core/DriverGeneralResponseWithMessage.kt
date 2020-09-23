package com.kopa.me.driver.domain.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverGeneralResponseWithMessage(
    @SerialName("statusCode")
    val statusCode: Int = 0,
    @SerialName("data")
    val data: DriverGeneralResponse,
    @SerialName("message")
    val message: String = "",
    @SerialName("status")
    val status: Boolean = false
)

@Serializable
data class DriverGeneralResponse(@SerialName("message") val message: String = "")


