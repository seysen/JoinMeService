package com.dataart.coreservice.controllers

import com.dataart.coreservice.dto.UserDto
import com.dataart.coreservice.services.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class AuthController(private val userService: UserService) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/register")
    fun register(@RequestBody body: UserDto) = with(body) {
        logger.info("Register request came: {}", desc())
        userService.register(this)
    }

    @PostMapping("/login")
    fun login(@RequestBody body: UserDto) = with(body) {
        logger.info("Login request came: {}", desc())
        userService.login(this)
    }
}
