package com.kopa.me.driver.data.datasource.remote.help

import com.kopa.me.driver.data.core.base.BaseDataSource
import com.kopa.me.driver.data.datasource.remote.login.api.FAQApi
import com.kopa.me.driver.domain.core.DriverGeneralRequestModel
import com.kopa.me.driver.domain.core.SimpleResult
import com.kopa.me.driver.domain.help.FAQResponseModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class FAQRemoteDataSourceImpl(private val faqAPI: FAQApi) :
    BaseDataSource(), IFAQRemoteDataSource {
    override suspend fun getDriverProfile(driverGeneralRequestModel: DriverGeneralRequestModel): SimpleResult<FAQResponseModel> =
        safeCall(call = {
            val request = Json { ignoreUnknownKeys = true }
                .encodeToJsonElement(
                    DriverGeneralRequestModel.serializer(),
                    driverGeneralRequestModel
                )
            val body = request.jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            faqAPI.fetchHelpTopics(body)
        })
}