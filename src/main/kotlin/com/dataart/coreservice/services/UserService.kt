package com.dataart.coreservice.services

import com.dataart.coreservice.dtos.UserDto
import com.dataart.coreservice.exception.UserAlreadyExistException
import com.dataart.coreservice.exception.WrongLoginOrPasswordException
import com.dataart.coreservice.mappers.UserMapper
import com.dataart.coreservice.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.collections.HashMap

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {
    private val bCryptPasswordEncoder = BCryptPasswordEncoder()

    private val logger: Logger = LoggerFactory.getLogger("com.dataart.coreservice.logback")

    fun register(body: UserDto): HashMap<String, Any> {
        val userFromDb = userRepository.findByEmail(body.email)
        logger.trace("Saving user ", body.desc())
        if (userFromDb.isEmpty) {
            var newUser = userMapper.toEntity(body)

            newUser.password = bCryptPasswordEncoder.encode(body.password)
            newUser = userRepository.save(newUser)
            val newUserId = newUser.id
            val response: HashMap<String, Any> = HashMap()

            val issuer = newUserId.toString()

            val jwt = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant())) // 1 day
                .signWith(SignatureAlgorithm.HS512, "secret").compact()

            response["userid"] = newUserId
            response["token"] = jwt
            logger.trace("Successful registration. Response: ", response)
            return response
        } else {
            logger.trace("User already exists ", body.desc())
            throw UserAlreadyExistException(userFromDb.get().email)
        }
    }

    fun login(body: UserDto): HashMap<String, Any> {
        logger.trace("Login user ", body.desc())
        val user = userRepository.findByEmail(body.email)
        if (user.isEmpty || !comparePassword(body.email, user.get().password)) {
            logger.trace("Wrong login or password ", body.desc())
            throw WrongLoginOrPasswordException() }

        val issuer = user.get().id.toString()

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000)) // 1 day
            .signWith(SignatureAlgorithm.HS512, "secret").compact()

        val response: HashMap<String, Any> = HashMap()

        response["userid"] = user.get().id
        response["token"] = jwt
        logger.trace("Successful login. Response: ", response)
        return response
    }

    fun comparePassword(email: String, password: String): Boolean {
        val hash = userRepository.findByEmail(email).get().password
        return hash == password
    }
}
