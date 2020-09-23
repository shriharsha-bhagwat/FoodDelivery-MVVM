package com.kopa.me.driver.domain.help

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FAQRequestModel(
    @SerialName("api_token")
    var api_token: String = "",
    @SerialName("driver_id")
    var driver_id: String = "",
    @SerialName("language")
    var language: String = "en"
)