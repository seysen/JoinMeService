package com.dataart.coreservice.services

import com.dataart.coreservice.dto.ResponseUserDto
import com.dataart.coreservice.dto.UserDto
import com.dataart.coreservice.exception.FillingFieldsException
import com.dataart.coreservice.exception.UserAlreadyExistException
import com.dataart.coreservice.exception.WrongLoginOrPasswordException
import com.dataart.coreservice.mappers.UserMapper
import com.dataart.coreservice.model.User
import com.dataart.coreservice.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    @Autowired private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    val jwtExpirationDays = 15L

    fun register(body: UserDto): ResponseUserDto {
        isRegisterDtoCorrect(body)
        var newUser = createUserByDto(body)

        logger.info("Saving user {}", body.desc())
        newUser = userRepository.save(newUser)

        val response = createResponse(newUser.id.toString())
        logger.info("Successful registration. Response: {}", response)
        return response
    }

    fun login(body: UserDto): ResponseUserDto {
        logger.info("User is trying to authorize {}", body.desc())
        val user = userRepository.findByEmail(body.email)
        isLoginDtoCorrect(body, user)
        val response = createResponse(user.get().id.toString())
        logger.info("Successful login. Response: {}", response)
        return response
    }

    private fun createUserByDto(body: UserDto) : User {
        val newUser = userMapper.toEntity(body)
        newUser.password = bCryptPasswordEncoder.encode(body.password)
        return newUser
    }

    fun comparePassword(passwordDb: String, passwordDto: String): Boolean {
        return bCryptPasswordEncoder.matches(passwordDto, passwordDb)
    }

    private fun isRegisterDtoCorrect(body: UserDto) {
        val userFromDb = userRepository.findByEmail(body.email)
        if(userFromDb.isPresent) {
            logger.info("User already exists {}", body.desc())
            throw UserAlreadyExistException(userFromDb.get().email)
        }
        if (body.name == "" || body.surname == "" || body.name == null || body.surname == null) {
            throw FillingFieldsException("Not all fields are filled in")
        }
    }

    private fun isLoginDtoCorrect(body: UserDto, user: Optional<User>) {
        if (user.isEmpty || !comparePassword(user.get().password, body.password)) {
            logger.info("Wrong login or password {}", body.desc())
            throw WrongLoginOrPasswordException()
        }
    }

    private fun createResponse(userId: String) : ResponseUserDto {
        val jwt = Jwts.builder()
            .setIssuer(userId)
            .setExpiration(Date.from(LocalDate.now().plusDays(jwtExpirationDays).atStartOfDay(ZoneId.systemDefault()).toInstant()))
            .signWith(SignatureAlgorithm.HS512, "secret").compact()

        return ResponseUserDto(userId, jwt)

    }
}