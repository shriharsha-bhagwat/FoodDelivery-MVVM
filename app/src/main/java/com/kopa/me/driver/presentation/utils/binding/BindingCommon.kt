package com.kopa.me.driver.presentation.utils.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kopa.me.driver.presentation.ui.common.BindableAdapter



object BindingCommon {
	
	/**
	 * apply Int Drawable res or color as resource in xml
	 * For example:
	 * android:src="@{viewModel.drawable}"
	 * val drawable = MutableLiveData<Int>(R.drawable.some_drawable)
	 */
	@JvmStatic
	@BindingAdapter("android:src")
	fun setImageResource(imageView: ImageView, resource: Int) {
		imageView.setImageResource(resource)
	}
	
	
	@JvmStatic
	@BindingAdapter("app:bindData")
	@Suppress("UNCHECKED_CAST")
	fun <T> setRecyclerViewAdapter(recyclerView: RecyclerView, newData: T) {
		if (recyclerView.adapter is BindableAdapter<*>) {
			(recyclerView.adapter as BindableAdapter<T>).setNewData(newData)
		}
	}
	
}