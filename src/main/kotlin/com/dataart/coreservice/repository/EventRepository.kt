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

    @Query(value = "from Event e where e.creatorId = :id")
    fun findAllByCreatorId(@Param("id") id: Long): Optional<List<Event>>
}
