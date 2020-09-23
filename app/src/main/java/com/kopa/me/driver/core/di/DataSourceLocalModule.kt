package com.kopa.me.driver.core.di

import com.kopa.me.driver.data.datasource.local.user.history.IUserHistoryLocalDataSource
import com.kopa.me.driver.data.datasource.local.user.history.UserHistoryLocalDataSourceImpl
import org.koin.dsl.module


/**
 * [DataSourceLocalModule] provides LocalDataSource instances
 */

val DataSourceLocalModule = module {

    single<IUserHistoryLocalDataSource> { UserHistoryLocalDataSourceImpl(dao = get()) }

}