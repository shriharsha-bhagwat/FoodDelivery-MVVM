package com.kopa.me.driver.data.repository.help

import com.kopa.me.driver.data.datasource.remote.help.IFAQRemoteDataSource
import com.kopa.me.driver.domain.core.DriverGeneralRequestModel
import com.kopa.me.driver.domain.core.ResultState
import com.kopa.me.driver.domain.core.SimpleResult
import com.kopa.me.driver.domain.help.HelpData
import com.kopa.me.driver.domain.help.IFAQRepository

/**
 * [IFAQRepository] implementation
 */

class FAQRepositoryImpl(private val dataSourceRemote: IFAQRemoteDataSource) :
    IFAQRepository {

    override suspend fun getFAQs(driverGeneralRequestModel: DriverGeneralRequestModel): SimpleResult<HelpData> =

        dataSourceRemote.getFAQs(driverGeneralRequestModel).fold(
            success = { dto -> ResultState.Success(dto.data) },
            failure = { throwable -> ResultState.Failure(throwable) }
        )
}