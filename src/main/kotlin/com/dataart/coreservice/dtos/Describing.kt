package com.dataart.coreservice.dtos

interface Describing {
    fun desc() =
        javaClass.declaredFields.joinToString(" ;") {
            "${it.name} = ${it.toString().take(15)}"
        }
}
