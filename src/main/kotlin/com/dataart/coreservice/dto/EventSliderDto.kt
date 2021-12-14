package com.dataart.coreservice.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
class EventSliderDto(
    val id: Long,

    var name: String,

    var description: String,

    var date: Instant? = null,

    @JsonProperty("link_ava")
    var linkAva: String,

    var users: Long,

    var likeEvents: Long
) : BaseDto()
