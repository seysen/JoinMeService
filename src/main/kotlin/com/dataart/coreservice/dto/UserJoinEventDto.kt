package com.dataart.coreservice.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserJoinEventDto(

    @JsonProperty("user_id")
    val userId: Long,

    @JsonProperty("event_id")
    val eventId: Long

) : BaseDto()
