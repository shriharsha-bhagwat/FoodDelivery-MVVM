package com.kopa.me.driver.domain.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverGeneralRequestModel(
    @SerialName("api_token")
    var api_token: String = "",
    @SerialName("driver_id")
    var driver_id: String = "",
    @SerialName("language")
    var language: String = "en"
)