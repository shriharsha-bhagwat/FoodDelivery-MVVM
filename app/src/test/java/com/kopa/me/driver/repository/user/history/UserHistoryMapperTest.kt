package com.kopa.me.driver.repository.user.history

import com.kopa.me.driver.core.utils.DateConverter
import com.kopa.me.driver.data.datasource.local.user.history.entities.UserHistoryEntity
import com.kopa.me.driver.data.repository.user.history.mappers.UserHistoryMappersFacade
import com.kopa.me.driver.domain.user.history.model.UserHistoryRecord
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 *
 */

@RunWith(JUnit4::class)
class UserHistoryMapperTest {

    private val mappers = UserHistoryMappersFacade()


    private val entityList: List<UserHistoryEntity> = listOf(
		UserHistoryEntity(
			historyEntryId = 1,
			commentary = "",
			timestamp = DateConverter.toDate("12-01-2020").time
		)
	)

    private val domainList: List<UserHistoryRecord> = listOf(
		UserHistoryRecord(
			id = 1,
			date = DateConverter.toDate("12-01-2020")
		)
	)


    @Test
    fun testMapDmHistoryToDb() {
        val mappingResult = mappers.mapDmHistoryToDb(domainList.first())
        assertTrue(mappingResult == entityList.first())
    }

    @Test
    fun testMapDbHistoryToDm() {
        val mappingResult = mappers.mapDbHistoryToDm(entityList)
        assertTrue(mappingResult == domainList)
    }
}