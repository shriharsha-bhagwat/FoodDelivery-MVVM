package com.kopa.me.driver.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kopa.me.driver.domain.driver.DriverProfileData
import com.kopa.me.driver.presentation.core.ViewState
import com.kopa.me.driver.presentation.core.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel() : BaseViewModel() {

    val driverByID: MutableLiveData<DriverProfileViewState> = MutableLiveData()

    /*val driverTaskData: MutableLiveData<DriverProfileData> = MutableLiveData()*/


    sealed class DriverProfileViewState : ViewState {
        object Loading : DriverProfileViewState()
        data class Success(val data: DriverProfileData?) : DriverProfileViewState()
        data class Error(val errorMessage: String) : DriverProfileViewState()
    }

    init {
        getMyTasks()
    }

    fun getMyTasks() {

        viewModelScope.launch {

            /*driverByID.value = DriverProfileViewState.Loading

            val driverProfileRequestModel = DriverProfileRequestModel(
                preferences.getString(
                    AppConstants.PREF_KEY_APP_TOKEN, ""
                ).orEmpty(), preferences.getLong(
                    AppConstants.PREF_KEY_DRIVER_ID, -1L
                ).toString(), "en"
            )

            repository.getDriverByDriverID(driverProfileRequestModel).fold({
                driverByID.value = DriverProfileViewState.Success(it)
            }, {
                driverByID.value = DriverProfileViewState.Error("Could not fetch profile details")
            })*/
        }
    }
}