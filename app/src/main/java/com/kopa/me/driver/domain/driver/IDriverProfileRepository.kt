package com.kopa.me.driver.domain.driver

import com.kopa.me.driver.domain.core.DriverGeneralResponse
import com.kopa.me.driver.domain.core.SimpleResult

interface IDriverProfileRepository {

    suspend fun getDriverByDriverID(driverProfileRequestModel: DriverProfileRequestModel): SimpleResult<DriverProfileData>

    suspend fun updateDriverDutyStatus(driverDutyStatusRequestModel: DriverDutyStatusRequestModel): SimpleResult<DriverGeneralResponse>
}