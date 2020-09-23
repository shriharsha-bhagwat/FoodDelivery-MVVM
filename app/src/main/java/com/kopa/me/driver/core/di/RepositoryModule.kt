package com.kopa.me.driver.core.di

import com.kopa.me.driver.data.repository.driver.DriverProfileProfileRepositoryImpl
import com.kopa.me.driver.data.repository.user.history.UserHistoryRepositoryImpl
import com.kopa.me.driver.data.repository.help.FAQRepositoryImpl
import com.kopa.me.driver.data.repository.login.LoginRepositoryImpl
import com.kopa.me.driver.data.repository.user.history.mappers.UserHistoryMappersFacade
import com.kopa.me.driver.domain.driver.IDriverProfileRepository
import com.kopa.me.driver.domain.user.history.IUserHistoryRepository
import com.kopa.me.driver.domain.help.IFAQRepository
import com.kopa.me.driver.domain.login.ILoginRepository
import org.koin.dsl.module

/**
 * [RepositoryModule] provides repositories instances
 * Repository often depends on local and remote DataSources
 * For example, @see [FuelPricesRepositoryImpl]
 */


val RepositoryModule = module {

	single<IFAQRepository> { FAQRepositoryImpl(dataSourceRemote = get())}
	single<ILoginRepository> { LoginRepositoryImpl(dataSourceRemote = get())}
	single<IUserHistoryRepository> {
		UserHistoryRepositoryImpl(dataSourceLocal = get(), mappers = UserHistoryMappersFacade())
	}
	single<IDriverProfileRepository> { DriverProfileProfileRepositoryImpl(dataSourceRemote = get())}
}

