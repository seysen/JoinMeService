package com.dataart.coreservice.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class ProfileDto (
    val profileName: String,
    //val linkAva: String
    ): BaseDto()