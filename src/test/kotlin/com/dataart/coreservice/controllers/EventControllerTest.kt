package com.dataart.coreservice.controllers

import com.dataart.coreservice.AbstractTestClass
import com.dataart.coreservice.dto.EventDto
import com.dataart.coreservice.model.Event
import com.dataart.coreservice.model.User
import com.dataart.coreservice.repository.EventRepository
import com.dataart.coreservice.repository.UserRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils.randomAlphabetic

class EventControllerTest(
    @Autowired val eventRepository: EventRepository,
    @Autowired val userRepository: UserRepository,
    @Autowired testRest: TestRestTemplate
) : AbstractTestClass(testRest) {

    private val EVENT_URI = "/events/"
    private val text_value_length: Int = 21

    @Test
    fun `Verify that POST endpoint works correctly`() {

        var expectedPostedEventId: Long = eventRepository.count() + 1

        var userId: Long = userRepository
            .save(
                User(
                    randomAlphabetic(text_value_length),
                    randomAlphabetic(text_value_length),
                    randomAlphabetic(text_value_length),
                    randomAlphabetic(text_value_length),
                    randomAlphabetic(text_value_length)
                )
            ).id

        var eventRequest = EventDto(
            randomAlphabetic(20),
            randomAlphabetic(40),
            randomAlphabetic(25),
            null,
            null,
            null,
            userId
        )

        with(
            testRest
                .postForEntity(EVENT_URI, eventRequest, Long::class.java)
        ) {
            statusCode shouldBe HttpStatus.OK
            body shouldNotBe null
            body shouldBe expectedPostedEventId
        }
    }

    @Test
    fun `Verify that GET by id endpoint works correctly`() {

        var user = userRepository
            .save(
                User(
                    randomAlphabetic(text_value_length),
                    randomAlphabetic(text_value_length),
                    randomAlphabetic(text_value_length),
                    randomAlphabetic(text_value_length),
                    randomAlphabetic(text_value_length)
                )
            )

        var event = eventRepository.save(
            Event(
                randomAlphabetic(text_value_length),
                randomAlphabetic(text_value_length),
                randomAlphabetic(text_value_length),
                user
            )
        )

        var eventId: Long = event.id

        with(testRest.getForEntity(EVENT_URI + eventId, EventDto::class.java)) {
            statusCode shouldBe HttpStatus.OK
            body shouldNotBe null
            body!!.name shouldBe event.name
            body!!.description shouldBe event.description
            body!!.linkAva shouldBe event.linkAva
            (body!!.createdDt!!).epochSecond shouldBe event.createdDt.epochSecond
            (body!!.updatedDt!!).epochSecond shouldBe event.updatedDt.epochSecond
            body!!.categories shouldBe event.categories
        }
    }
}
