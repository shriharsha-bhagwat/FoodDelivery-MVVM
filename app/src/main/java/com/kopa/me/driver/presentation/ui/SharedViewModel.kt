package com.kopa.me.driver.presentation.ui

import androidx.lifecycle.MutableLiveData
import com.kopa.me.driver.presentation.core.ViewState
import com.kopa.me.driver.presentation.core.base.BaseViewModel
import com.kopa.me.driver.presentation.ui.common.LoadingState
import com.kopa.me.driver.presentation.ui.fuel.history.FuelHistoryViewState
import com.kopa.me.driver.presentation.ui.fuel.prices.FuelPricesViewState

/**
 * This is the documentation block about the class
 */

class SharedViewModel : BaseViewModel() {
	
	val showLoading: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.HIDE)
	
	fun handleLoading(state: ViewState) {
		when (state) {
			is FuelHistoryViewState.Loading -> showLoading.value = LoadingState.SHOW
			is FuelPricesViewState.Loading -> showLoading.value = LoadingState.SHOW
			else -> showLoading.value = LoadingState.HIDE
		}
	}
}