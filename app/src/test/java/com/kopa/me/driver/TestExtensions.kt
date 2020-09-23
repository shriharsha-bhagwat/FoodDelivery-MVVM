package com.kopa.me.driver

import java.io.File

fun readJson(fileName: String) =
	File("src/test/java/com/kopa/me/driver/res/${fileName}.json").readText()