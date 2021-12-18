package com.dataart.coreservice.repository

import com.dataart.coreservice.model.Event
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.Instant
import java.util.*

interface EventRepository : CrudRepository<Event, Long> {
    fun findEventsByDateAfter(date: Instant, pageable: Pageable): Page<Event>

    @Query(nativeQuery = true, value = "select * from events join users_events on events.id = users_events.event_id where user_id = :id")
    fun findAllByUserId(@Param("id") id: Long): List<Event>
}
