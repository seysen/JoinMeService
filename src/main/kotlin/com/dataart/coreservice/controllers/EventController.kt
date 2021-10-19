package com.dataart.coreservice.controllers

import com.dataart.coreservice.dto.EventDto
import com.dataart.coreservice.mappers.EventMapper
import com.dataart.coreservice.services.EventService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events")
class EventController(private val eventService: EventService, private val eventMapper: EventMapper) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun add(@RequestBody eventRequest: EventDto) = with(eventRequest) {
        logger.info("addEvent request: {}", desc())
        eventService.add(this).also {
        logger.info("addEvent response: {}", it)
        }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = with(id) {
        logger.info("getEvent request: {}", this)
        eventService.getById(this)
            .let { eventMapper.convertToEventDtoResponse(it) }
            .also { logger.info("getEvent response: {}", it.desc()) }
    }
}
