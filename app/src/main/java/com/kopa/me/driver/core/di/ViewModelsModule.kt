package com.kopa.me.driver.core.di

import com.kopa.me.driver.presentation.ui.SharedViewModel
import com.kopa.me.driver.presentation.ui.care.CareViewModel
import com.kopa.me.driver.presentation.ui.fuel.history.FuelHistoryViewModel
import com.kopa.me.driver.presentation.ui.fuel.prices.FuelPricesViewModel
import com.kopa.me.driver.presentation.ui.home.HomeViewModel
import com.kopa.me.driver.presentation.ui.home.ProfileDetailViewModel
import com.kopa.me.driver.presentation.ui.home.ProfileViewModel
import com.kopa.me.driver.presentation.ui.login.LoginViewModel
import com.kopa.me.driver.presentation.ui.mycar.MyCarViewModel
import com.kopa.me.driver.presentation.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * [ViewModelsModule] provides ViewModel instances
 */

val ViewModelsModule = module {
    viewModel { HomeViewModel() }
    viewModel { ProfileViewModel(repository = get(), loginRepository = get()) }
    viewModel { CareViewModel() }
    viewModel { MyCarViewModel() }
    viewModel { FuelPricesViewModel(repository = get()) }
    viewModel { FuelHistoryViewModel(repository = get()) }
    viewModel { SettingsViewModel() }
    viewModel { LoginViewModel(repository = get()) }
    viewModel { SharedViewModel() }
    viewModel { ProfileDetailViewModel(repository = get())}
}