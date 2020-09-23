package com.kopa.me.driver.core.di

import com.kopa.me.driver.data.datasource.remote.driver.DriverProfileRemoteDataSourceImpl
import com.kopa.me.driver.data.datasource.remote.driver.IDriverProfileRemoteDataSource
import com.kopa.me.driver.data.datasource.remote.help.FAQRemoteDataSourceImpl
import com.kopa.me.driver.data.datasource.remote.help.IFAQRemoteDataSource
import com.kopa.me.driver.data.datasource.remote.login.ILoginRemoteDataSource
import com.kopa.me.driver.data.datasource.remote.login.LoginRemoteDataSourceImpl
import org.koin.dsl.module


/**
 * [DataSourceRemoteModule] provides RemoteDataSource instances
 */

val DataSourceRemoteModule = module {

	single<IFAQRemoteDataSource> { FAQRemoteDataSourceImpl(faqAPI = get()) }
	single<ILoginRemoteDataSource> { LoginRemoteDataSourceImpl(loginApi = get()) }
	single<IDriverProfileRemoteDataSource> { DriverProfileRemoteDataSourceImpl(driverProfileApi = get()) }
}