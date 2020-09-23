package com.kopa.me.driver.domain.login

import com.kopa.me.driver.domain.core.DriverGeneralRequestModel
import com.kopa.me.driver.domain.core.SimpleResult

interface ILoginRepository {

    suspend fun authenticate(loginRequestModel: LoginRequestModel): SimpleResult<LoginResponseModel>

    suspend fun unAuthenticate(driverGeneralRequestModel: DriverGeneralRequestModel): SimpleResult<LogoutResponseModel>

}