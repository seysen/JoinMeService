package com.dataart.coreservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class CoreServiceApplication

fun main(args: Array<String>) {
    runApplication<CoreServiceApplication>(*args)
}
