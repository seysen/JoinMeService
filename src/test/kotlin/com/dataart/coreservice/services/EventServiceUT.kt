package com.dataart.coreservice.services

import com.dataart.coreservice.AbstractTestClass
import com.dataart.coreservice.dto.EventDto
import com.dataart.coreservice.exception.EventNotFoundException
import com.dataart.coreservice.exception.UserNotFoundException
import com.dataart.coreservice.model.Event
import com.dataart.coreservice.model.User
import com.dataart.coreservice.repository.EventRepository
import com.dataart.coreservice.repository.UserRepository
import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.shouldBe
import io.kotest.spring.SpringListener
import io.mockk.every
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.util.Optional

class EventServiceUT : AbstractTestClass() {

    override fun listeners() = listOf(SpringListener)

    @MockkBean
    private lateinit var eventRepository: EventRepository

    @MockkBean
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var eventService: EventService

    init {
        val userId: Long = 0
        val date: Instant = Instant.now()

        var user = User(
            "user",
            "user",
            "user",
            "user",
            "user"
        )

        var eventRequest = EventDto(
            "name",
            "desc",
            date,
            "link",
            null,
            null,
            null,
            userId
        )

        var event = Event("name", "desc", date, "link", user)

        "test UserNotFoundException when adding new event" - {

            "UserNotFoundException is thrown" {

                val userNotExistsId: Long = 2

                user.id = userNotExistsId
                eventRequest.creatorId = userNotExistsId

                every { userRepository.findById(userNotExistsId) } returns Optional.empty()

                runCatching {
                    eventService.add(eventRequest)
                }.onFailure {
                    it.message shouldBe "User with id " + userNotExistsId.toString() + " was NOT FOUND"
                    (it is UserNotFoundException).shouldBe(true)
                }

                verify { userRepository.findById(userNotExistsId) }
            }

            "UserNotFoundException is not thrown" {

                var userExistsId: Long = 11
                var savedEventId: Long = 33

                user.id = userExistsId
                eventRequest.creatorId = userExistsId

                every { userRepository.findById(userExistsId) } returns Optional.of(user)
                every { (eventRepository.save(event)).id } returns savedEventId

              //  Assertions.assertThat(eventService.add(eventRequest).equals(savedEventId))
                 eventService.add(eventRequest) shouldBe savedEventId

                verify { userRepository.findById(userExistsId) }
                verify { eventRepository.save(event) }
            }
        }

        "test EventNotFoundException when getting event by id" - {

            "EventNotFoundException is thrown" {

                val eventNotExistsId: Long = 105

                every { eventRepository.findById(eventNotExistsId) } returns Optional.empty()

                runCatching {
                    eventService.getById(eventNotExistsId)
                }.onFailure {
                    it.message shouldBe "Event with id " + eventNotExistsId.toString() + " was NOT FOUND"
                    (it is EventNotFoundException).shouldBe(true)
                }

                verify { eventRepository.findById(eventNotExistsId) }
            }

            "EventNotFoundException is not thrown" {

                val eventExistsId: Long = 205
                event.id = eventExistsId

                every { eventRepository.findById(eventExistsId) } returns Optional.of(event)

                 eventService.getById(eventExistsId) shouldBe event
              //  Assertions.assertThat(eventService.getById(eventExistsId).equals(event))
                verify { eventRepository.findById(eventExistsId) }
            }
        }
    }
}
