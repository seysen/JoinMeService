package com.dataart.coreservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class EventNotFoundException(eventId: Long) : RuntimeException("Event with id $eventId was NOT FOUND")
