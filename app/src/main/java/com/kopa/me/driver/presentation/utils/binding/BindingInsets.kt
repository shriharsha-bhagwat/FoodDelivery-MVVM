package com.kopa.me.driver.presentation.utils.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.kopa.me.driver.presentation.utils.applySystemWindowInsetsMargins
import com.kopa.me.driver.presentation.utils.applySystemWindowInsetsPadding

/**
 * Contains BindingAdapters which relates to windowInsets
 */

object BindingInsets {
	
	/**
	 * Apply padding to corresponded view
	 * Specify each parameter individually
	 */
	@JvmStatic
	@BindingAdapter(
		"app:applySystemWindowInsetsPaddingLeft",
		"app:applySystemWindowInsetsPaddingTop",
		"app:applySystemWindowInsetsPaddingRight",
		"app:applySystemWindowInsetsPaddingBottom",
		requireAll = false
	)
	fun applySystemWindowInsetsPadding(
		view: View,
		applyLeft: Boolean,
		applyTop: Boolean,
		applyRight: Boolean,
		applyBottom: Boolean
	) = view.applySystemWindowInsetsPadding(applyLeft, applyTop, applyRight, applyBottom)
	
	/**
	 * Apply margins to corresponded view
	 * Specify each parameter individually
	 */
	@JvmStatic
	@BindingAdapter(
		"app:applySystemWindowInsetsMarginLeft",
		"app:applySystemWindowInsetsMarginTop",
		"app:applySystemWindowInsetsMarginRight",
		"app:applySystemWindowInsetsMarginBottom",
		requireAll = false
	)
	fun applySystemWindowInsetsMargin(
		view: View,
		applyLeft: Boolean,
		applyTop: Boolean,
		applyRight: Boolean,
		applyBottom: Boolean
	) = view.applySystemWindowInsetsMargins(applyLeft, applyTop, applyRight, applyBottom)
	
}