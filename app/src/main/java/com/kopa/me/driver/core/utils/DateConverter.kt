package com.kopa.me.driver.core.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Used to convert dates
 */

object DateConverter {
	private val getMonthIntFormatter = SimpleDateFormat("MM", Locale.ENGLISH)
	
	private val getMonthTextFormatter = SimpleDateFormat("MMMM", Locale.getDefault())
	
	//used to make http request with such pattern as argument
	private val networkRequestFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
	
	//used primary to display in UI
	private val humanFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
	
	
	fun toText(date: Date?): String = date?.let { humanFormat.format(it) } ?: ""
	
	fun toDate(text: String): Date = humanFormat.parse(text)
	
	
	fun toFuelPriceRequestString(date: Date): String = networkRequestFormat.format(date)
	
	/**
	 * Make an Int Month from a date
	 */
	fun Date?.getMonthInt(): Int =  this?.let { getMonthIntFormatter.format(it).toInt() } ?: 0
	
	/**
	 * Make a full month text
	 */
	fun Date?.getMonthText(): String = this?.let { getMonthTextFormatter.format(it) } ?: ""
	
}