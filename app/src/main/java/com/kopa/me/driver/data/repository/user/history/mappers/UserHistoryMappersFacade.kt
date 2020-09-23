package com.kopa.me.driver.data.repository.user.history.mappers

import com.kopa.me.driver.data.core.mappers.mapList
import com.kopa.me.driver.data.datasource.local.user.history.entities.UserHistoryEntity
import com.kopa.me.driver.domain.user.history.model.UserHistoryRecord
import java.util.*

/**
 * MappersFacade for multiple mappers used in UserHistoryRepositoryImpl
 * contains mappers between layers [data -> domain]
 * mappers between sources [DomainModel -> dbEntity], [dbEntity -> DomainModel]
 *
 * DTO = Data Transfer Object
 * DB = database
 * DM = Domain Model
 */

class UserHistoryMappersFacade {

    val mapDmHistoryToDb: (UserHistoryRecord) -> UserHistoryEntity = { record ->
        UserHistoryEntity(
			historyEntryId = record.id,
			commentary = record.commentary,
			timestamp = record.date.time
		)
    }


    val mapDbHistoryToDm: (List<UserHistoryEntity>) -> List<UserHistoryRecord> = { input ->
        mapList(input) { entity ->
            UserHistoryRecord(
				id = entity.historyEntryId,
				commentary = entity.commentary,
				date = Date(entity.timestamp)
			)
        }
    }

}