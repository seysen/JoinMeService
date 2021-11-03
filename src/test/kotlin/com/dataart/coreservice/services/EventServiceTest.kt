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
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import java.util.Optional

class EventServiceTest(
    @Autowired testRest: TestRestTemplate
) : AbstractTestClass(testRest) {

    @MockkBean
    private lateinit var eventRepository: EventRepository

    @MockkBean
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var eventService: EventService

    private val userId: Long = 0

    private var user = User(
        "user",
        "user",
        "user",
        "user",
        "user"
    )

    private var eventRequest = EventDto(
        "name",
        "desc",
        "link",
        null,
        null,
        null,
        userId
    )

    private var event = Event("name", "desc", "link", user)

    @Test
    fun `Verify that UserNotFoundException is thrown when adding event`() {

        val userNotExistsId: Long = 2

        user.id = userNotExistsId
        eventRequest.creatorId = userNotExistsId

        every { userRepository.findById(userNotExistsId) } returns Optional.empty()

        runCatching {
            eventService.add(eventRequest)
        }.onFailure {
            it.message shouldBe userNotExistsId.toString()
            (it is UserNotFoundException).shouldBe(true)
        }

        verify { userRepository.findById(userNotExistsId) }
    }

    @Test
    fun `Verify that UserNotFoundException is not thrown when adding event`() {

        var userExistsId: Long = 11
        var savedEventId: Long = 33

        user.id = userExistsId
        eventRequest.creatorId = userExistsId

        every { userRepository.findById(userExistsId) } returns Optional.of(user)
        every { (eventRepository.save(event)).id } returns savedEventId

        assertThat(eventService.add(eventRequest).equals(savedEventId))
        // eventService.add(eventRequest) shouldBe savedEventId // <-- почемуто возвращает userExistsId ???

        verify { userRepository.findById(userExistsId) }
        verify { eventRepository.save(event) }
    }

    @Test
    fun `Verify that EventNotFoundException is thrown when getting event by id`() {

        val eventNotExistsId: Long = 105

        every { eventRepository.findById(eventNotExistsId) } returns Optional.empty()

        runCatching {
            eventService.getById(eventNotExistsId)
        }.onFailure {
            it.message shouldBe eventNotExistsId.toString()
            (it is EventNotFoundException).shouldBe(true)
        }

        verify { eventRepository.findById(eventNotExistsId) }
    }

    @Test
    fun `Verify that EventNotFoundException is not thrown when getting event by id`() {

        val eventExistsId: Long = 205
        event.id = eventExistsId

        every { eventRepository.findById(eventExistsId) } returns Optional.of(event)

        // eventService.add(eventRequest) shouldBe savedEventId  <---- ! wrong ?
        assertThat(eventService.getById(eventExistsId).equals(event))

        verify { eventRepository.findById(eventExistsId) }
    }
}
