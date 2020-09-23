package com.kopa.me.driver.data.datasource.remote.login

import com.kopa.me.driver.data.core.base.BaseDataSource
import com.kopa.me.driver.data.datasource.remote.login.api.LoginApi
import com.kopa.me.driver.domain.core.DriverGeneralRequestModel
import com.kopa.me.driver.domain.core.SimpleResult
import com.kopa.me.driver.domain.login.LoginRequestModel
import com.kopa.me.driver.domain.login.LoginResponseModel
import com.kopa.me.driver.domain.login.LogoutResponseModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


class LoginRemoteDataSourceImpl(private val loginApi: LoginApi) :
    BaseDataSource(), ILoginRemoteDataSource {


    override suspend fun authenticate(loginRequestModel: LoginRequestModel): SimpleResult<LoginResponseModel> =
        safeCall(call = {
            val request = Json { ignoreUnknownKeys = true }
                .encodeToJsonElement(LoginRequestModel.serializer(), loginRequestModel)
            val body = request.jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            loginApi.authenticateUser(body)
        })

    override suspend fun unAuthenticate(driverGeneralRequestModel: DriverGeneralRequestModel): SimpleResult<LogoutResponseModel> =
        safeCall(call = {
            val request = Json { ignoreUnknownKeys = true }
                .encodeToJsonElement(
                    DriverGeneralRequestModel.serializer(),
                    driverGeneralRequestModel
                )
            val body = request.jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            loginApi.unAuthenticateUser(body)
        })
}