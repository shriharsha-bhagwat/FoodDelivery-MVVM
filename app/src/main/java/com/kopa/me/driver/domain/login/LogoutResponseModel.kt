package com.kopa.me.driver.domain.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogoutResponseModel(
    @SerialName("statusCode")
    val statusCode: Int = 0,
    @SerialName("message")
    val message: String = "",
    @SerialName("status")
    val status: Boolean = false
)
