package com.kopa.me.driver.data.datasource.remote.login.api

import com.kopa.me.driver.domain.core.DriverGeneralResponseWithMessage
import com.kopa.me.driver.domain.driver.DriverProfileResponseModel
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface DriverProfileApi {

    private companion object {
        private const val BASE_URL = "/api/driver"
    }

    @POST("$BASE_URL/v1/driver-profile")
    suspend fun getDriverProfile(@Body requestBody: RequestBody): DriverProfileResponseModel

    @POST("$BASE_URL/v1/online-status-update")
    suspend fun updateOnlineDutyStatus(@Body requestBody: RequestBody): DriverGeneralResponseWithMessage

    @POST("$BASE_URL/v1/offline-status-update")
    suspend fun updateOfflineDutyStatus(@Body requestBody: RequestBody): DriverGeneralResponseWithMessage

    @POST("$BASE_URL/v1/driver-location-update")
    suspend fun updateLocationStatus(@Body requestBody: RequestBody): DriverGeneralResponseWithMessage

    @POST("$BASE_URL/v1/help-faq")
    suspend fun fetchHelpTopics(@Body requestBody: RequestBody): DriverGeneralResponseWithMessage

}