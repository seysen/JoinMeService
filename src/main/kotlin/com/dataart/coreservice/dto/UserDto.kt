package com.dataart.coreservice.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
class UserDto(
    @JsonProperty("name")
    var name: String? = null,

    @JsonProperty("surname")
    var surname: String? = null,

    @JsonProperty("email")
    var email: String,

    @JsonProperty("password")
    var password: String,

    @JsonProperty("createdDt")
    var createdDt: Instant = Instant.now()

) : BaseDto()
