package com.dataart.coreservice.repository

import com.dataart.coreservice.model.Event
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import java.time.Instant

interface EventRepository : CrudRepository<Event, Long> {
    fun findEventsByDateAfter(date: Instant, pageable: Pageable): Page<Event>
}
