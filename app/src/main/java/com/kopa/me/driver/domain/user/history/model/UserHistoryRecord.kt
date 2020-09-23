package com.kopa.me.driver.domain.user.history.model

import com.kopa.me.driver.core.utils.DateConverter
import com.kopa.me.driver.core.utils.DateConverter.getMonthText
import java.util.*


/**
 *
 */

data class UserHistoryRecord(
	val id: Long,
	val commentary: String = "",
	val date: Date = Date(),
) {
    val dateText = DateConverter.toText(date)
    val dateMonthText: String = date.getMonthText()
}