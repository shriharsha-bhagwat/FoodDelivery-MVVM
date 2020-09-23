
package com.kopa.me.driver.presentation.ui.common.custom.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


/**
 * Grid decoration to force same gaps between items
 */

class GridItemDecoration: ItemDecoration() {

	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

		val position = parent.getChildAdapterPosition(view)
		val spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex

		// Add top margin only for the first item to avoid double space between items
		if (position == 0 || position == 1) {
			outRect.top = 30
		}

		if (spanIndex == 0) {
			outRect.left = 30
			outRect.right = 15
		}
		else { //if you just have 2 span . Or you can use (staggeredGridLayoutManager.getSpanCount()-1) as last span
			outRect.left = 15
			outRect.right = 30
		}
		outRect.bottom = 30
	}

}