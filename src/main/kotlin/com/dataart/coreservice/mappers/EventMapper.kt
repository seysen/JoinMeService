package com.dataart.coreservice.mappers

import com.dataart.coreservice.dto.EventDto
import com.dataart.coreservice.model.Event
import com.dataart.coreservice.model.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface EventMapper {

    @Mappings(
        Mapping(target = "creatorId", ignore = true),
        Mapping(
            target = "categories",
            expression = "java(event.getCategories().stream().map(c->c.getName()).collect(java.util.stream.Collectors.toList()))"
        ))
    fun convertToEventDtoResponse(event: Event): EventDto

    // expression для categories нужен тк mapstruct преобразует
    // fun categoryToString(category : Category) : String {return category.name} в пустую строку
    // и в имплементации генерит код return new String()

    // target  поле в сущносте- результате

        @Mappings( // указать все поля которые есть в event dto + все из эвент
        Mapping(target = "name", source = "eventDto.name"),
        Mapping(target = "linkAva", source = "eventDto.linkAva"),
        Mapping(target = "description", source = "eventDto.description"),
        Mapping(target = "creatorId", source = "user"),
        Mapping(target = "categories", ignore = true),
        Mapping(target = "updatedDt", ignore = true),
        Mapping(target = "createdDt", ignore = true),
        Mapping(target = "id", ignore = true),
        Mapping(target = "likeEvents", ignore = true),
        Mapping(target = "messages", ignore = true),
        Mapping(target = "photos", ignore = true),
        )
        fun convertEventDtoRequestToEvent(eventDto: EventDto, user: User): Event
}
