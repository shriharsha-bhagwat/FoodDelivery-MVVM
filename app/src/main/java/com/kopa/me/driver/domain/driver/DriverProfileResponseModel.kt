package com.kopa.me.driver.domain.driver

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverProfileResponseModel(
    @SerialName("status_code")
    val statusCode: Int = 0,
    @SerialName("data")
    val data: Data,
    @SerialName("message")
    val message: String = "",
    @SerialName("status")
    val status: Boolean = false
)

@Serializable
data class DriverProfileData(
    @SerialName("zipcode")
    val zipcode: String = "",
    @SerialName("firstname")
    val firstname: String = "",
    @SerialName("phone")
    val phone: String = "",
    @SerialName("address2")
    val address2: String = "",
    @SerialName("city")
    val city: String = "",
    @SerialName("address1")
    val address1: String = "",
    @SerialName("email")
    val email: String = "",
    @SerialName("driver_image")
    val driverImage: String = "",
    @SerialName("lastname")
    val lastname: String = ""
)

@Serializable
data class Data(
    @SerialName("profile_data")
    val profileData: DriverProfileData
)