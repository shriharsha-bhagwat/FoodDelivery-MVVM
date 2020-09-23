package com.kopa.me.driver.domain.driver

import com.kopa.me.driver.core.utils.AppConstants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverDutyStatusRequestModel(
    @SerialName("api_token")
    var api_token: String = "",
    @SerialName("driver_id")
    var driver_id: Long = -1L,
    @SerialName("language")
    var language: String = "en",
    @SerialName("latitude")
    var latitude: Double = 0.0,
    @SerialName("longitude")
    var longitude: Double = 0.0,


    var type: Int = AppConstants.DRIVER_STATUS_OFF_DUTY
)