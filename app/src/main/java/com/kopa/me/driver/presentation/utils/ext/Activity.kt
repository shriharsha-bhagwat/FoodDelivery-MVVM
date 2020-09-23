package com.kopa.me.driver.presentation.utils.ext

import android.app.Activity
import android.widget.Toast
import com.kopa.me.driver.presentation.utils.keyboard.FluidContentResizer

fun Activity.actAsFluid() = FluidContentResizer.listen(this)

fun Activity.toast(message: String?) =
    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
