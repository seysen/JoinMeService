package com.dataart.coreservice.controllers

import com.dataart.coreservice.AbstractTestClass
import com.dataart.coreservice.dto.EventDto
import com.dataart.coreservice.model.Event
import com.dataart.coreservice.model.User
import com.dataart.coreservice.repository.EventRepository
import com.dataart.coreservice.repository.UserRepository
import io.kotest.matchers.shouldBe
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils.randomAlphabetic
import java.time.Instant

class EventControllerIT(
    @Autowired val eventRepository: EventRepository,
    @Autowired val userRepository: UserRepository,
) : AbstractTestClass() {

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
            Instant.now(),
            randomAlphabetic(25),
            null,
            null,
            null,
            userId
        )

        Given {
            spec(requestSpecification)
            request().body(eventRequest).port(port)
        } When {
            post(EVENT_URI)
        } Then {
            statusCode(200)
            body(notNullValue())
            body(equalTo(expectedPostedEventId.toString()))
        }
    }
    // негативные сценарии проверяются в сервисе

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
                Instant.now(),
                randomAlphabetic(text_value_length),
                user
            )
        )

        var eventId: Long = event.id
        var responseCreateddDt = ""
        var responseUpdatedDt = ""

        Given {
            spec(requestSpecification).port(port)
        } When {
            get(EVENT_URI + eventId)
        } Then {
            statusCode(200)
            body(notNullValue())
            body("description", equalTo(event.description))
            body("link_ava", equalTo(event.linkAva))
            body("category", equalTo(event.categories))
        } Extract {
            responseUpdatedDt = path("updated_dt")
            responseCreateddDt = path("updated_dt")
        }

        Instant.parse(responseCreateddDt).epochSecond shouldBe event.createdDt.epochSecond
        Instant.parse(responseUpdatedDt).epochSecond shouldBe event.updatedDt.epochSecond
    }
}
