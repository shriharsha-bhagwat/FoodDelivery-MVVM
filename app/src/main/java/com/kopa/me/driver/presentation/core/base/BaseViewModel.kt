package com.kopa.me.driver.presentation.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ironz.binaryprefs.Preferences
import com.kopa.me.driver.core.utils.logDebug
import com.kopa.me.driver.presentation.utils.Event
import org.koin.java.KoinJavaComponent


/**
 * generic class for viewModels
 */

abstract class BaseViewModel: ViewModel() {

	/*inline fun <T> launchOnViewModelScope(crossinline block: suspend () -> LiveData<T>): LiveData<T> {
		return liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
			emitSource(block())
		}
	}

	inline fun <T> launchOnViewModelScope(crossinline block: suspend () -> Unit) {
		viewModelScope.launch(Dispatchers.IO) {
			block()
		}
	}*/

	protected val _onErrorEvent: MutableLiveData<Event<Any>> = MutableLiveData()
	val onErrorEvent: LiveData<Event<Any>> = _onErrorEvent

	protected val _onMessageEvent: MutableLiveData<Event<Any>> = MutableLiveData()
	val onMessageEvent: LiveData<Event<Any>> = _onMessageEvent

	protected val _onFinishActivityEvent: MutableLiveData<Event<Unit>> = MutableLiveData()
	val onFinishActivityEvent: LiveData<Event<Unit>> = _onFinishActivityEvent

	protected val preferences: Preferences by KoinJavaComponent.inject(Preferences::class.java)

	override fun onCleared() {
		logDebug(message = "${javaClass.simpleName} on cleared called")
		super.onCleared()
	}

	fun onBackButtonPress() {
		_onFinishActivityEvent.value = Event(Unit)
	}
}