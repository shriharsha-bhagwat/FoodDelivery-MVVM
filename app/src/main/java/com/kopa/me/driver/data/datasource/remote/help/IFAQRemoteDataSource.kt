package com.kopa.me.driver.data.datasource.remote.help

import com.kopa.me.driver.domain.core.DriverGeneralRequestModel
import com.kopa.me.driver.domain.core.SimpleResult
import com.kopa.me.driver.domain.help.FAQResponseModel

interface IFAQRemoteDataSource {

    suspend fun getFAQs(driverGeneralRequestModel: DriverGeneralRequestModel): SimpleResult<FAQResponseModel>

}