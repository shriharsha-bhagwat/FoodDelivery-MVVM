

package com.kopa.me.driver.presentation.ui.mycar

import androidx.lifecycle.MutableLiveData
import com.kopa.me.driver.presentation.core.base.BaseViewModel

/**
 *
 */

class MyCarViewModel : BaseViewModel() {
	
	val myCar: MutableLiveData<MyCarFragment.CarInDropDown> = MutableLiveData()
	
}