package com.dataart.coreservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
class WrongLoginOrPasswordException : RuntimeException("Invalid username or password")
