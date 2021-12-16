package com.dataart.coreservice.controllers

import com.dataart.coreservice.dto.EventDto
import com.dataart.coreservice.mappers.EventMapper
import com.dataart.coreservice.services.EventService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Arrays.toString

@RestController
@RequestMapping("/profile")
class ProfileController(private val eventService: EventService, private val eventMapper: EventMapper) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/{id}")
    fun getAllEventsByUserId(@PathVariable id: Long): List<EventDto> {
        logger.info("User' profile is going to be shown")
        return eventService.getAllByUserId(id)
            .let { eventMapper.convertToEventDtoResponse(it) }
            .also{ logger.info("getAllEventsByUserId response {}", it.toString()) }
    }

}