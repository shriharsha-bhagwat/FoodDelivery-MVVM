package com.kopa.me.driver.data.datasource.remote.driver

import com.kopa.me.driver.domain.core.DriverGeneralResponseWithMessage
import com.kopa.me.driver.domain.core.SimpleResult
import com.kopa.me.driver.domain.driver.DriverDutyStatusRequestModel
import com.kopa.me.driver.domain.driver.DriverProfileRequestModel
import com.kopa.me.driver.domain.driver.DriverProfileResponseModel

interface IDriverProfileRemoteDataSource {

    suspend fun getDriverProfile(driverProfileRequestModel: DriverProfileRequestModel): SimpleResult<DriverProfileResponseModel>

    suspend fun updateDriverDutyStatus(driverDutyStatusRequestModel: DriverDutyStatusRequestModel): SimpleResult<DriverGeneralResponseWithMessage>

}