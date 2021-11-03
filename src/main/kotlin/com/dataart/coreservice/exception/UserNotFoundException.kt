package com.dataart.coreservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class UserNotFoundException(userId: Long) : RuntimeException("User with id $userId was NOT FOUND")
