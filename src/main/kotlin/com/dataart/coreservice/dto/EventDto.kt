package com.dataart.coreservice.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class EventDto(

    var name: String,

    var description: String,

    @JsonProperty("link_ava")
    var linkAva: String,

    @JsonProperty("created_dt")
    var createdDt: Instant? = null,

    @JsonProperty("updated_dt")
    var updatedDt: Instant? = null,

    @JsonProperty("category")
    var categories: List<String>? = null,

    @JsonProperty("creator_id")
    var creatorId: Long? = null

) : BaseDto()
