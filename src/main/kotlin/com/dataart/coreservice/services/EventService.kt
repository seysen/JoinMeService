package com.dataart.coreservice.services

import com.dataart.coreservice.dto.EventDto
import com.dataart.coreservice.exception.EventNotFoundException
import com.dataart.coreservice.exception.UserNotFoundException
import com.dataart.coreservice.mappers.EventMapper
import com.dataart.coreservice.repository.EventRepository
import com.dataart.coreservice.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository,
    private val eventMapper: EventMapper
) {

    private val getEventCount = 10
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    val pageable: Pageable = Pageable.ofSize(getEventCount)

    fun add(eventRequest: EventDto) =
        eventRequest
          .let {
              userRepository
                  .findById(it.creatorId!!)
                  .orElseThrow {
                      UserNotFoundException(it.creatorId!!)
                          .also { logger.error("Exception occurred", it) }
                  }
          }
          .let {
              eventRepository.save(eventMapper.convertEventDtoRequestToEvent(eventRequest, it))
          }
          .let {
                logger.info("service: event added {}", it.toString())
                it.id
          }

    fun getById(id: Long) = with(id) {
        eventRepository.findById(this)
            .orElseThrow {
                EventNotFoundException(this)
                    .also { logger.error("Exception occurred", it) }
            }.also {
                logger.info("service: event found {}", it.toString())
            }
    }

    fun getEvents() = eventRepository
        .findEventsByDateAfter(Instant.now(), pageable).toList()
        .shuffled()
        .also {
            logger.debug("Service: events fount {}", it)
        }
}
