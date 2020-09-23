
package com.kopa.me.driver.presentation.ui.common.custom.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Linear recyclerView ItemDecoration used to make same gaps between all elements
 */

class LinearItemDecoration (private val orientation: Int = RecyclerView.VERTICAL): RecyclerView.ItemDecoration() {

	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

		val position = parent.getChildAdapterPosition(view)

		// Add top margin only for the first item to avoid double space between items
		if (position == 0 && orientation == RecyclerView.VERTICAL) outRect.top = 20

		outRect.left = 15
		outRect.right = 15
		outRect.bottom = 20
	}

}