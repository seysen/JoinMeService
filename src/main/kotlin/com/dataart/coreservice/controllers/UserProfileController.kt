package com.dataart.coreservice.controllers

import com.dataart.coreservice.dto.ProfileDto
import com.dataart.coreservice.mappers.UserMapper
import com.dataart.coreservice.services.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/userprofile")
class UserProfileController(private val userService: UserService, private val userMapper: UserMapper) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getProfileById(@RequestParam("id") id: Long): ProfileDto {
        logger.info("User' profile is going to be shown")
        return userService.getUserById(id)
            .let { userMapper.toProfile(it) }
            .also{ logger.info("getAllEventsByUserId response {}", it.toString()) }
    }

}