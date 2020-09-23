package com.kopa.me.driver.domain.driver

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverProfileRequestModel(
    @SerialName("api_token")
    var api_token: String = "",
    @SerialName("driver_id")
    var driver_id: String = "",
    @SerialName("language")
    var language: String = "en"
)