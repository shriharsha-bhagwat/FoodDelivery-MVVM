package com.kopa.me.driver.domain.help

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FAQResponseModel(
    @SerialName("status_code")
    val statusCode: Int = 0,
    @SerialName("data")
    val data: HelpData,
    @SerialName("message")
    val message: String = "",
    @SerialName("status")
    val status: Boolean = false
)

@Serializable
data class HelpData(
    @SerialName("help_data")
    val helpData: List<HelpDataItem>?
)

@Serializable
data class HelpDataItem(
    @SerialName("Help with delivery")
    val helpWithDelivery: List<HelpWithDeliveryItem>?
)

@Serializable
data class HelpWithDeliveryItem(
    @SerialName("Ans")
    val ans: String = "",
    @SerialName("Qus")
    val qus: String = ""
)


