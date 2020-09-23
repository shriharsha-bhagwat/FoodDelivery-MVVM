package com.kopa.me.driver.core.utils

/**
 * App level constants
 */

object AppConstants {

    const val BROADCAST_MESSAGE_START_LOCATION_UPDATE : String = "START_LOCATION_UPDATE"
    const val BROADCAST_MESSAGE_STOP_LOCATION_UPDATE : String = "STOP_LOCATION_UPDATE"
    const val BROADCAST_MESSAGE : String = "MESSAGE"

    const val TAG: String = "KOPA"

    const val RESPONSE_CODE_UNAUTH: Int = 401
    const val RESPONSE_CODE_SUCCESS: Int = 200

    const val PREF_KEY_APP_TOKEN = "PREF_KEY_APP_TOKEN"
    const val PREF_KEY_DRIVER_ID = "PREF_KEY_DRIVER_ID"
    const val PREF_KEY_DRIVER_DUTY_STATUS = "PREF_KEY_DRIVER_DUTY_STATUS"

    const val DRIVER_STATUS_OFF_DUTY = 0
    const val DRIVER_STATUS_ON_DUTY = 1
    const val DRIVER_STATUS_UPDATE = 2

    const val APP_INTENT_LOCATION_UPDATE: String = "android.app.kopa.location.update"

}

