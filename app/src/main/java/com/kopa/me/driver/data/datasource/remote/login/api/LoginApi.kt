package com.kopa.me.driver.data.datasource.remote.login.api

import com.kopa.me.driver.domain.login.LoginResponseModel
import com.kopa.me.driver.domain.login.LogoutResponseModel
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    private companion object {
        private const val BASE_URL = "/api/driver"
    }

    @POST("$BASE_URL/v1/login")
    suspend fun authenticateUser(@Body requestBody: RequestBody): LoginResponseModel

    @POST("$BASE_URL/v1/logout")
    suspend fun unAuthenticateUser(@Body requestBody: RequestBody): LogoutResponseModel

}