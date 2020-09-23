package com.kopa.me.driver.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kopa.me.driver.R
import com.kopa.me.driver.core.utils.AppConstants
import com.kopa.me.driver.domain.core.DriverGeneralRequestModel
import com.kopa.me.driver.domain.driver.DriverDutyStatusRequestModel
import com.kopa.me.driver.domain.driver.DriverProfileData
import com.kopa.me.driver.domain.driver.DriverProfileRequestModel
import com.kopa.me.driver.domain.driver.IDriverProfileRepository
import com.kopa.me.driver.domain.login.ILoginRepository
import com.kopa.me.driver.presentation.core.ViewState
import com.kopa.me.driver.presentation.core.base.BaseViewModel
import com.kopa.me.driver.presentation.utils.Event
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: IDriverProfileRepository,
    private val loginRepository: ILoginRepository
) : BaseViewModel() {

    val driverByID: MutableLiveData<DriverProfileViewState> = MutableLiveData()
    val driverProfileData: MutableLiveData<DriverProfileData> = MutableLiveData()

    private val _onLogout: MutableLiveData<Event<Unit>> = MutableLiveData()
    val onLogout: LiveData<Event<Unit>> = _onLogout

    private val _onProfileDetailEvent: MutableLiveData<Event<Unit>> = MutableLiveData()
    val onProfileDetailEvent: LiveData<Event<Unit>> = _onProfileDetailEvent

    protected val _onDutyStatusChangeEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val onDutyStatusChangeEvent: LiveData<Event<Boolean>> = _onDutyStatusChangeEvent

    sealed class DriverProfileViewState : ViewState {
        object Loading : DriverProfileViewState()
        data class Success(val data: DriverProfileData?) : DriverProfileViewState()
        data class Error(val errorMessage: String) : DriverProfileViewState()
    }

    init {
        getMyProfile()
    }

    fun getMyProfile() {

        viewModelScope.launch {

            driverByID.value = DriverProfileViewState.Loading

            val driverProfileRequestModel = DriverProfileRequestModel(
                preferences.getString(
                    AppConstants.PREF_KEY_APP_TOKEN, ""
                ).orEmpty(), preferences.getLong(
                    AppConstants.PREF_KEY_DRIVER_ID, -1L
                ).toString(), "en"
            )

            repository.getDriverByDriverID(driverProfileRequestModel).fold({
                driverProfileData.value = it
                driverByID.value = DriverProfileViewState.Success(it)
            }, {
                driverByID.value = DriverProfileViewState.Error("Could not fetch profile details")
            })
        }
    }

    fun onLogout() {

        viewModelScope.launch {

            driverByID.value = DriverProfileViewState.Loading

            val driverProfileRequestModel = DriverGeneralRequestModel(
                preferences.getString(
                    AppConstants.PREF_KEY_APP_TOKEN, ""
                ).orEmpty(), preferences.getLong(
                    AppConstants.PREF_KEY_DRIVER_ID, -1L
                ).toString(), "en"
            )

            loginRepository.unAuthenticate(driverProfileRequestModel).fold({
                _onLogout.value = Event(Unit)

                _onErrorEvent.value = Event("Logout Success!")
            }, {
                _onErrorEvent.value = Event("Could not logout!")
            })

            driverByID.value = DriverProfileViewState.Success(null)
        }
    }

    fun onDutyStatusChange(isOnDuty: Boolean) {

        viewModelScope.launch {

            driverByID.value = DriverProfileViewState.Loading

            val driverDutyStatusRequestModel = DriverDutyStatusRequestModel(
                preferences.getString(
                    AppConstants.PREF_KEY_APP_TOKEN, ""
                ).orEmpty(), preferences.getLong(
                    AppConstants.PREF_KEY_DRIVER_ID, -1L
                ), "en", 0.0, 0.0
            )

            if (isOnDuty) {
                driverDutyStatusRequestModel.type = AppConstants.DRIVER_STATUS_ON_DUTY
            } else {
                driverDutyStatusRequestModel.type = AppConstants.DRIVER_STATUS_OFF_DUTY
            }

            repository.updateDriverDutyStatus(driverDutyStatusRequestModel).fold({
                if (isOnDuty) {
                    _onDutyStatusChangeEvent.value = Event(true)
                    _onMessageEvent.value = Event(R.string.online_status_success)
                } else {
                    _onDutyStatusChangeEvent.value = Event(false)
                    _onMessageEvent.value = Event(R.string.offline_status_success)
                }
            }, {
                _onDutyStatusChangeEvent.value = Event(false)
                _onErrorEvent.value = Event(R.string.duty_status_set_error)
            })

            driverByID.value = DriverProfileViewState.Success(null)
        }
    }

    fun onProfileDetail() {
        _onProfileDetailEvent.value = Event(Unit)
    }

}