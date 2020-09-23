package com.kopa.me.driver.domain.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestModel(
    @SerialName("email")
    var email: String = "",
    @SerialName("password")
    var password: String = "",
    @SerialName("language")
    var language: String = "en"
)
