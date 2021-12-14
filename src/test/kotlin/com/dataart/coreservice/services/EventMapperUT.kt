package com.dataart.coreservice.services

import com.dataart.coreservice.AbstractTestClass
import com.dataart.coreservice.dto.EventDto
import com.dataart.coreservice.mappers.EventMapper
import com.dataart.coreservice.model.Category
import com.dataart.coreservice.model.Event
import com.dataart.coreservice.model.User
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant

class EventMapperUT(@Autowired private val eventMapper: EventMapper) : AbstractTestClass() {

    var userId: Long = 1
    var user = User("user", "user", "e", "psw", "link_ava")

    init {
        user.id = userId
    }

    @Test
    fun `Verify that Event Request is converted correctly to Event`() {

        var eventDtoRequest = EventDto(
            "new event", "description", Instant.now(), "link_ava",
            null, null, null, userId
        )

        var result = eventMapper.convertEventDtoRequestToEvent(eventDtoRequest, user)

        (result is Event).shouldBe(true)
        result.name shouldBe eventDtoRequest.name
        result.description shouldBe eventDtoRequest.description
        result.linkAva shouldBe eventDtoRequest.linkAva
        result.creatorId.id shouldBe userId
        result.id shouldBe 0L
        result.categories shouldBe mutableListOf()
        result.photos shouldBe mutableListOf()
        result.likeEvents shouldBe mutableListOf()
        result.createdDt shouldNotBe null
        result.updatedDt shouldNotBe null
    }

    @Test
    fun `Verify that Event is converted correctly to Event Response`() {

        var categoryNameFirst = "activity"
        var categoryNameSecond = "activity2"

        var event = Event(
            "new event", "description", Instant.now(), "link_ava",
            user
        )

        var categories: MutableList<Category> = mutableListOf()
        categories.add(Category(categoryNameFirst))
        categories.add(Category(categoryNameSecond))

        event.categories.addAll(categories)

        var eventDtoResponse = eventMapper.convertToEventDtoResponse(event)

        (eventDtoResponse is EventDto).shouldBe(true)
        eventDtoResponse.name shouldBe event.name
        eventDtoResponse.description shouldBe event.description
        eventDtoResponse.linkAva shouldBe event.linkAva
        eventDtoResponse.categories?.size shouldBe categories.size
        eventDtoResponse.categories?.get(0) shouldBe categoryNameFirst
        eventDtoResponse.categories?.get(1) shouldBe categoryNameSecond
    }
}
