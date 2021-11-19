package com.dataart.coreservice.repository

import com.dataart.coreservice.model.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>

    fun save(user: User): User
}
