package com.kopa.me.driver.domain.help

import com.kopa.me.driver.domain.core.DriverGeneralRequestModel
import com.kopa.me.driver.domain.core.SimpleResult

interface IFAQRepository {

    suspend fun getFAQs(driverGeneralRequestModel: DriverGeneralRequestModel): SimpleResult<HelpData>

}