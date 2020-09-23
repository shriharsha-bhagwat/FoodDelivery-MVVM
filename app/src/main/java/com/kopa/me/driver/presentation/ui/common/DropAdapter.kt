
package com.kopa.me.driver.presentation.ui.common

import android.content.Context
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes

/**
 * Generic adapter for Drop Down Lists used in AutoCompleteTextViews
 */

abstract class DropAdapter<T>(
	context: Context,
	@LayoutRes private val layoutId: Int,
	private val data: ArrayList<T>
): ArrayAdapter<T>(context, layoutId, data) {
	
	override fun getItem(position: Int): T = data[position]
	
	override fun getCount(): Int = data.size
}