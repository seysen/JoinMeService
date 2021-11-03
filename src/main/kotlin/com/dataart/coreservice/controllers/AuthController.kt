package com.dataart.coreservice.controllers

import com.dataart.coreservice.dtos.UserDto
import com.dataart.coreservice.services.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val userService: UserService) {

    private val logger: Logger = LoggerFactory.getLogger("com.dataart.coreservice.logback")

    @PostMapping("/register")
    fun register(@RequestBody body: UserDto) = with(body) {
        logger.trace("Register request came: ", desc())
        userService.register(this)
    }

    @PostMapping("/login")
    fun login(@RequestBody body: UserDto) = with(body) {
        logger.trace("Login request came: ", desc())
        userService.login(this)
    }
}
