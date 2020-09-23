package com.kopa.me.driver.data.repository.login

import com.kopa.me.driver.data.datasource.remote.login.ILoginRemoteDataSource
import com.kopa.me.driver.domain.core.DriverGeneralRequestModel
import com.kopa.me.driver.domain.core.ResultState
import com.kopa.me.driver.domain.core.SimpleResult
import com.kopa.me.driver.domain.login.ILoginRepository
import com.kopa.me.driver.domain.login.LoginRequestModel
import com.kopa.me.driver.domain.login.LoginResponseModel
import com.kopa.me.driver.domain.login.LogoutResponseModel

/**
 * [ILoginRepository] implementation
 */

class LoginRepositoryImpl(private val dataSourceRemote: ILoginRemoteDataSource) : ILoginRepository {

    override suspend fun authenticate(loginRequestModel: LoginRequestModel): SimpleResult<LoginResponseModel> =
        dataSourceRemote.authenticate(loginRequestModel).fold(
            success = { dto -> ResultState.Success(dto) },
            failure = { throwable -> ResultState.Failure(throwable) }
        )

    override suspend fun unAuthenticate(driverGeneralRequestModel: DriverGeneralRequestModel): SimpleResult<LogoutResponseModel> =
        dataSourceRemote.unAuthenticate(driverGeneralRequestModel).fold(
            success = { dto -> ResultState.Success(dto) },
            failure = { throwable -> ResultState.Failure(throwable) }
        )
}