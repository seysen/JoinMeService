package com.dataart.coreservice.dto
const val LENGTH = 15

open class BaseDto {

    fun desc(): String =
        this.javaClass.declaredFields.joinToString(";") {
            it.also {
                it.isAccessible = true
            }
            "${it.name} = ${
                it.get(this)?.let {
                    it.toString().take(LENGTH)
                }
            }"
        }
}

// не могу отследить закономерность появления ошибки, появилась после ввода последней версии desc()

/* после добавления последней версии desc() см стр 5 base dto
 периодически стала вылетать ошибка
 nested exception is org.springframework.dao.DataIntegrityViolationException: not-null property
 references a null or transient value :
 com.dataart.coreservice.model.Event.createdDt; ссылка на sercice add сохранение в репозитории

 те ругается на то что сохраняем эвент с полем createdDt null а оно помечено not null
 хотя у нас стоит аннолация @CreatedDate которая сохраняет в бд время

 иногда выбрасывается иногда нет не понимаю от чего зависит

 заметила что когда выбрасывается если убрать nullable = false то ошидка исчезает
   Event
 @CreatedDate
 @Column(nullable = false) <------ пришлось убрать nullable = false
 var createdDt: Instant = Instant.now()
 */
