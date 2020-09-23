package com.kopa.me.driver.presentation.utils.ext

import androidx.databinding.ViewDataBinding

inline fun <reified T : ViewDataBinding> T.use(crossinline block: T.() -> Unit = {}) {
    block()
    executePendingBindings()
}
