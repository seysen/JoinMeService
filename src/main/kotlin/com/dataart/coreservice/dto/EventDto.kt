package com.dataart.coreservice.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class EventDto(

    var name: String,

    var description: String,

    @JsonProperty("link_ava")
    var linkAva: String,

    @JsonProperty("created_dt")
    var createdDt: Instant? = null,

    @JsonProperty("updated_dt")
    var updatedDt: Instant? = null,

    @JsonProperty("category")
    var categories: List<String>? = null,

    @JsonProperty("creator_id")
    var creatorId: Long? = null

) {

//    fun desc(): String =
//        "EventDto (name = $name; description = ${description.take(15)}; " +
//        "linkAva = $linkAva; createdDt = $createdDt; updatedDt = $updatedDt; " +
//        "categories = $categories; creatorId = $creatorId)"

    fun desc(): String =
        this.javaClass.declaredFields.joinToString(";") {
            it.also {
                it.isAccessible = true
            }
            "${it.name} = ${it.get(this)?.let{
                it.toString().take(15)
            }}"
        }

    // без this выбрасывает ошибку про transient value null в event
    // без {it.get(this) просто печатает тип поля
    // без проверки на ноль выбрасывает null pointer ex
    // без  it.isAccessible = true выбрасывает ошибку нет доступа


}
