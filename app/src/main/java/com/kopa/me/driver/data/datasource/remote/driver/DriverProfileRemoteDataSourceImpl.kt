package com.kopa.me.driver.data.datasource.remote.driver

import com.kopa.me.driver.core.utils.AppConstants
import com.kopa.me.driver.data.core.base.BaseDataSource
import com.kopa.me.driver.data.datasource.remote.login.api.DriverProfileApi
import com.kopa.me.driver.domain.core.DriverGeneralResponseWithMessage
import com.kopa.me.driver.domain.core.SimpleResult
import com.kopa.me.driver.domain.driver.DriverDutyStatusRequestModel
import com.kopa.me.driver.domain.driver.DriverProfileRequestModel
import com.kopa.me.driver.domain.driver.DriverProfileResponseModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class DriverProfileRemoteDataSourceImpl(private val driverProfileApi: DriverProfileApi) :
    BaseDataSource(), IDriverProfileRemoteDataSource {

    override suspend fun getDriverProfile(driverProfileRequestModel: DriverProfileRequestModel): SimpleResult<DriverProfileResponseModel> =
        safeCall(call = {
            val request = Json { ignoreUnknownKeys = true }
                .encodeToJsonElement(
                    DriverProfileRequestModel.serializer(),
                    driverProfileRequestModel
                )
            val body = request.jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            driverProfileApi.getDriverProfile(body)
        })


    override suspend fun updateDriverDutyStatus(driverDutyStatusRequestModel: DriverDutyStatusRequestModel): SimpleResult<DriverGeneralResponseWithMessage> =

        safeCall(call = {
            val request = Json { ignoreUnknownKeys = true }
                .encodeToJsonElement(
                    DriverDutyStatusRequestModel.serializer(),
                    driverDutyStatusRequestModel
                )
            val body = request.jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


            if (driverDutyStatusRequestModel.type == AppConstants.DRIVER_STATUS_UPDATE) {
                driverProfileApi.updateLocationStatus(body)
            } else if (driverDutyStatusRequestModel.type == AppConstants.DRIVER_STATUS_ON_DUTY) {
                driverProfileApi.updateOnlineDutyStatus(body)
            } else {
                driverProfileApi.updateOfflineDutyStatus(body)
            }
        })
}