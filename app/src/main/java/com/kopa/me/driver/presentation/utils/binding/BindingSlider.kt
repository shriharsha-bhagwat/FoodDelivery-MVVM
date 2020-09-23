package com.kopa.me.driver.presentation.utils.binding

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.slider.Slider

/**
 * Contains [Slider] value binding methods
 */

object BindingSlider {
	
	//getter for slider value
	@JvmStatic
	@InverseBindingAdapter(attribute = "android:value")
	fun getSliderValue(slider: Slider): Float = slider.value
	
	//change listener for slider value
	@JvmStatic
	@BindingAdapter("android:valueAttrChanged")
	fun setSliderListeners(slider: Slider, attrChange: InverseBindingListener) {
		slider.addOnChangeListener { _, _, _ ->
			attrChange.onChange()
		}
	}
	
}