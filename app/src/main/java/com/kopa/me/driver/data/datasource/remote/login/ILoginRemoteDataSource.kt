package com.kopa.me.driver.data.datasource.remote.login

import com.kopa.me.driver.domain.core.DriverGeneralRequestModel
import com.kopa.me.driver.domain.core.SimpleResult
import com.kopa.me.driver.domain.login.LoginRequestModel
import com.kopa.me.driver.domain.login.LoginResponseModel
import com.kopa.me.driver.domain.login.LogoutResponseModel

interface ILoginRemoteDataSource {

    suspend fun authenticate(loginRequestModel: LoginRequestModel): SimpleResult<LoginResponseModel>

    suspend fun unAuthenticate(driverGeneralRequestModel: DriverGeneralRequestModel): SimpleResult<LogoutResponseModel>

}