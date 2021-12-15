package com.dataart.coreservice.repository

import com.dataart.coreservice.model.Event
import com.dataart.coreservice.model.UserEvent
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface UserEventRepository : CrudRepository<UserEvent, Long>{

   fun findByUserIdAndEventId(@Param("userId") userId : Long, @Param("eventId") eventId : Long) : Optional<UserEvent>
}
