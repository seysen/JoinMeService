package com.dataart.coreservice.repository

import com.dataart.coreservice.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long>
