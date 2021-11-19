package com.dataart.coreservice.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
class ResponseUserDto (
    @JsonProperty("userid")
    var userId: String,

    @JsonProperty("token")
    var token: String
)