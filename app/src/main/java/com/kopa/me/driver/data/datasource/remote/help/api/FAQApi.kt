package com.kopa.me.driver.data.datasource.remote.login.api

import com.kopa.me.driver.domain.help.FAQResponseModel
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface FAQApi {

    private companion object {
        private const val BASE_URL = "/api/driver"
    }

    @POST("$BASE_URL/v1/help-faq")
    suspend fun fetchHelpTopics(@Body requestBody: RequestBody): FAQResponseModel

}