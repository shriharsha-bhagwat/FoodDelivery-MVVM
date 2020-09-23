package com.kopa.me.driver.datasource.remote

import com.kopa.me.driver.data.datasource.local.user.history.IUserHistoryLocalDataSource
import com.kopa.me.driver.modules.DatabaseTestModule
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

/**
 * Tests for [IFuelPricesLocalDataSource]
 */

@RunWith(JUnit4::class)
class FuelHistoryRemoteDataSourceTest : KoinTest {

    private val dataSource: IUserHistoryLocalDataSource by inject()

    /**
     * Override default Koin configuration to use Room in-memory database
     */
    @Before
    fun before() {
        loadKoinModules(DatabaseTestModule)
    }
}