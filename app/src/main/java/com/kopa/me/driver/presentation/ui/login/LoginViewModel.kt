package com.kopa.me.driver.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kopa.me.driver.domain.login.Data
import com.kopa.me.driver.domain.login.ILoginRepository
import com.kopa.me.driver.domain.login.LoginRequestModel
import com.kopa.me.driver.presentation.core.base.BaseViewModel
import com.kopa.me.driver.presentation.utils.Event
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginViewModel(private val repository: ILoginRepository) : BaseViewModel() {

    private val _onLoginEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val onLoginEvent: LiveData<Event<Boolean>> = _onLoginEvent

    private val _onLoginSuccessEvent: MutableLiveData<Event<Data>> = MutableLiveData()
    val onLoginSuccessEvent: LiveData<Event<Data>> = _onLoginSuccessEvent

    private val _onDataLoading: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val onDataLoading: LiveData<Event<Boolean>> = _onDataLoading

    val emailInputValue: MutableLiveData<String> = MutableLiveData()
    val passwordInputValue: MutableLiveData<String> = MutableLiveData()

    fun onClickLogin() {
        viewModelScope.launch {
            if (!emailInputValue.value.isNullOrEmpty()) {
                if (!passwordInputValue.value.isNullOrEmpty()) {

                    emailInputValue.value?.let { _email ->
                        passwordInputValue.value?.let { _password ->

                            if (isValidEmailAddress(_email)) {
                                _onLoginEvent.value = Event(true)

                                val loginRequestModel = LoginRequestModel(_email, _password, "en")
                                repository.authenticate(loginRequestModel).fold(
                                    success = { _onLoginSuccessEvent.value = Event(it.data) },
                                    failure = {
                                        _onErrorEvent.value = Event(it.localizedMessage)
                                        _onLoginEvent.value = Event(false)
                                    }
                                )
                            } else {
                                _onErrorEvent.value = Event("Please enter a valid Email address")
                            }
                        }
                    }
                } else {
                    _onErrorEvent.value = Event("Please enter your password")
                }
            } else {
                _onErrorEvent.value = Event("Please enter your Email address")
            }
        }
    }

    private fun isValidEmailAddress(emailAddress: String): Boolean {
        val emailRegEx: String
        val pattern: Pattern
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$"
        pattern = Pattern.compile(emailRegEx)
        val matcher: Matcher = pattern.matcher(emailAddress)
        return if (!matcher.find()) {
            false
        } else true
    }

}