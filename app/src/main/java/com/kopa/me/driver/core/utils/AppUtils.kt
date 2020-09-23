package com.kopa.me.driver.core.utils

import android.content.Context
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException

object AppUtils {

    fun isLocationEnabled(context: Context): Boolean {
        val locationMode: Int
        locationMode = try {
            Settings.Secure
                .getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
        } catch (e: SettingNotFoundException) {
            return false
        }
        return locationMode != Settings.Secure.LOCATION_MODE_OFF
    }

}
