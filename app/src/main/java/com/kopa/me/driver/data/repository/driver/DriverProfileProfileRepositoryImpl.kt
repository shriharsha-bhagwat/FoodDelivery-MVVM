package com.kopa.me.driver.data.repository.driver

import com.kopa.me.driver.data.datasource.remote.driver.IDriverProfileRemoteDataSource
import com.kopa.me.driver.domain.core.DriverGeneralResponse
import com.kopa.me.driver.domain.core.ResultState
import com.kopa.me.driver.domain.core.SimpleResult
import com.kopa.me.driver.domain.driver.DriverDutyStatusRequestModel
import com.kopa.me.driver.domain.driver.DriverProfileData
import com.kopa.me.driver.domain.driver.DriverProfileRequestModel
import com.kopa.me.driver.domain.driver.IDriverProfileRepository

/**
 * [IDriverProfileRemoteDataSource] implementation
 */

class DriverProfileProfileRepositoryImpl(private val dataSourceRemote: IDriverProfileRemoteDataSource) :
    IDriverProfileRepository {

    override suspend fun getDriverByDriverID(driverProfileRequestModel: DriverProfileRequestModel): SimpleResult<DriverProfileData> =

        dataSourceRemote.getDriverProfile(driverProfileRequestModel).fold(
            success = { dto -> ResultState.Success(dto.data.profileData) },
            failure = { throwable -> ResultState.Failure(throwable) }
        )

    override suspend fun updateDriverDutyStatus(driverDutyStatusRequestModel: DriverDutyStatusRequestModel): SimpleResult<DriverGeneralResponse> =

        dataSourceRemote.updateDriverDutyStatus(driverDutyStatusRequestModel).fold(
            success = { dto -> ResultState.Success(dto.data) },
            failure = { throwable -> ResultState.Failure(throwable) }
        )

}