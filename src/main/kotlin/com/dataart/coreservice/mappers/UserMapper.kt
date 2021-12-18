package com.dataart.coreservice.mappers

import com.dataart.coreservice.dto.ProfileDto
import com.dataart.coreservice.dto.UserDto
import com.dataart.coreservice.model.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper {

    @Mappings(
    Mapping(target = "name", source = "userDto.name"),
    Mapping(target = "surname", source = "userDto.surname"),
    Mapping(target = "email", source = "userDto.email"),
    Mapping(target = "password", source = "userDto.password"),
    Mapping(target = "linkAva", ignore = true),
    )
    fun toEntity(userDto: UserDto): User

    @Mappings(
        Mapping( target = "profileName", expression = "java(user.getName() + \" \" + user.getSurname())"),
    )

    fun toProfile(user: User): ProfileDto
}
