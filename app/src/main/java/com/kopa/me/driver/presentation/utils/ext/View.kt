package com.kopa.me.driver.presentation.utils.ext

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.view.View
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kopa.me.driver.R
import com.kopa.me.driver.presentation.utils.Event

inline var View.isVisible: Boolean
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

inline var View.isHidden: Boolean
    get() = visibility == GONE
    set(value) {
        visibility = if (value) GONE else VISIBLE
    }

inline var View.isInvisible: Boolean
    get() = visibility == INVISIBLE
    set(value) {
        visibility = if (value) INVISIBLE else VISIBLE
    }

fun View.show() {
    visibility = VISIBLE
}

fun View.invisible() {
    visibility = INVISIBLE
}

fun View.hide() {
    visibility = GONE
}

fun View.hideKeyBoard() {
    val inputMethodManager =
            context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Triggers a snackbar message when the value contained by snackbarMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<Int>>,
    timeLength: Int
) {

    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            showSnackbar(context.getString(it), timeLength)
        }
    })
}


/**
 * Triggers a snackbar message when the value contained by snackbarMessageLiveEvent is modified.
 */
fun View.setupCommonSnackbar(
        lifecycleOwner: LifecycleOwner,
        snackbarEvent: LiveData<Event<Any>>,
        timeLength: Int
) {

    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            if (it is Int) {
                showSnackbar(context.getString(it), timeLength)
            } else if (it is String) {
                showSnackbar(it, timeLength)
            }
        }
    })
}


/**
 * Triggers a snackbar message when the value contained by snackbarMessageLiveEvent is modified.
 */
fun View.setupCustomSnackbar(
        lifecycleOwner: LifecycleOwner,
        snackbarEvent: LiveData<Event<String>>,
        timeLength: Int
) {

    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            showSnackbar(it, timeLength)
        }
    })
}

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    val snackbar = Snackbar.make(this, snackbarText, timeLength);
    val view: View = snackbar.getView()
    snackbar.setActionTextColor(Color.RED)

    view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.colorOnBackground))

    val textView = view.findViewById<TextView>(R.id.snackbar_text)
    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_base_warning, 0, 0, 0)
    textView.compoundDrawablePadding = 6
    textView.setTextColor(Color.WHITE)
    textView.textAlignment = TEXT_ALIGNMENT_CENTER

    //snackbar.apply {view.layoutParams = (view.layoutParams as CoordinatorLayout.LayoutParams).apply {setMargins(leftMargin, topMargin, rightMargin, 60)}}
    snackbar.show()

}

/**
 * Extension function to smooth scroll to top of an item on a certain position
 */
fun RecyclerView.smoothSnapToPosition(position: Int, snapMode: Int = LinearSmoothScroller.SNAP_TO_START) {
    val smoothScroller = object : LinearSmoothScroller(this.context) {
        override fun getVerticalSnapPreference(): Int = snapMode
        override fun getHorizontalSnapPreference(): Int = snapMode
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}

fun View.showOrHide() {
    if (visibility == VISIBLE) {
        visibility = GONE
    } else {
        visibility = VISIBLE
    }
}

fun SpannableStringBuilder.spansAppend(
        text: CharSequence,
        flags: Int,
        vararg spans: Any
): SpannableStringBuilder {
    val start = length
    append(text)

    spans.forEach { span ->
        setSpan(span, start, length, flags)
    }

    return this
}