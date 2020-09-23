package com.kopa.me.driver.presentation.utils.ext

import android.content.Context
import android.view.LayoutInflater

inline val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)
