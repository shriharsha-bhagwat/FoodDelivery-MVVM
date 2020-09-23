package com.kopa.me.driver.presentation.utils.binding

import android.animation.ValueAnimator
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.kopa.me.driver.core.utils.roundTo
import com.kopa.me.driver.presentation.utils.binding.BindingTextView.mapOfDoubleValues
import com.kopa.me.driver.presentation.utils.binding.BindingTextView.mapOfIntValues

/**
 * Contains binding methods related to [TextView]
 *
 * [mapOfIntValues] and [mapOfDoubleValues] contains Pair of [TextView] id and [Number] used
 * to smooth animate from last value instead of jumping to 0 each time function invoked
 */

object BindingTextView {
	
	private val mapOfIntValues = HashMap<Int, Int>()
	private val mapOfDoubleValues = HashMap<Int, Double>()
	
	@JvmStatic
	@BindingAdapter("app:animateInt", "app:textTemplate", "app:defaultText", requireAll = true)
	fun setAnimatedValueInt(textView: TextView, value: Int, template: String, defaultText: String) {
		val intAnimator = ValueAnimator.ofInt().apply {
			duration = 1200
			addUpdateListener { textView.text = template.format(it.animatedValue.toString()) }
		}
		
		if (value > 0) {
			//init start and end values
			with(mapOfIntValues[textView.id] ?: 0) {
				/** check if stored value is same with given [value] if so -> set 0 as start */
				if (this == value) intAnimator.setIntValues(0, value)
				else intAnimator.setIntValues(this, value)
			}
			intAnimator.start()
			mapOfIntValues[textView.id] = value
		}
		else {
			textView.text = defaultText
			mapOfIntValues[textView.id] = 0
		}
	}
	
	
	@JvmStatic
	@BindingAdapter("app:animateDouble", "app:textTemplate", "app:defaultText", requireAll = true)
	fun setAnimatedValueDouble(textView: TextView, value: Double, template: String, defaultText: String) {
		val floatAnimator = ValueAnimator.ofFloat().apply {
			duration = 1200
			addUpdateListener {
				textView.text = template.format((it.animatedValue as Float).roundTo(2).toString())
			}
		}
		
		if (value > 0) {
			//init start to end values
			with(mapOfDoubleValues[textView.id] ?: 0.0 ) {
				if (this == value) floatAnimator.setFloatValues(0f, value.toFloat())
				else floatAnimator.setFloatValues(this.toFloat(), value.toFloat())
			}
			floatAnimator.start()
			mapOfDoubleValues[textView.id] = value
		}
		else {
			textView.text = defaultText
			mapOfDoubleValues[textView.id] = 0.0
		}
	}

}