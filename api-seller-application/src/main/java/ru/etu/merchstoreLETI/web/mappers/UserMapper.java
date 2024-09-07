package ru.etu.merchstoreLETI.web.mappers;

import org.mapstruct.Mapper;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.web.dto.user.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto>{
}

