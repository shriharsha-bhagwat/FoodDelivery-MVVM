package com.kopa.me.driver.presentation.utils.binding

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.kopa.me.driver.presentation.utils.hideKeyboard

/**
 * Contains methods to hide or show views depends on specific actions
 */

object BindingVisibility {
	
	/**
	 * Hides keyboard when the [EditText] is focused.
	 *
	 * There can only be one [TextView.OnEditorActionListener] on each [EditText] and
	 * this [BindingAdapter] sets it.
	 */
	@JvmStatic
	@BindingAdapter("app:hideKeyboardOnInputDone")
	fun hideKeyboardOnInputDone(inputView: EditText, enabled: Boolean) {
		if (!enabled) return
		val listener = TextView.OnEditorActionListener { _, actionId, _ ->
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				inputView.hideKeyboard(inputView)
			}
			false
		}
		inputView.setOnEditorActionListener(listener)
	}
	
	@JvmStatic
	@BindingAdapter("app:visibilityInvisible")
	fun setVisibilityInvisible(view: View, show: Boolean = false) {
		view.visibility = if (show) View.VISIBLE else View.INVISIBLE
	}
	
	@JvmStatic
	@BindingAdapter("app:visibilityGone")
	fun setVisibilityGone(view: View, show: Boolean = false) {
		view.visibility = if (show) View.VISIBLE else View.GONE
	}
	
}