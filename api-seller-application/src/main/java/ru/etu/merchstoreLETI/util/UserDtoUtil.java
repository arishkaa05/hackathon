package ru.etu.merchstoreLETI.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.web.dto.user.UserDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDtoUtil implements EntityDtoUtil<UserDto, User> {
    @Override
    public  UserDto toDto(User entityModel) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(entityModel,dto);
        return dto;
    }

    @Override
    public  User toEntity(UserDto entityModelDto) {
        User user = new User();
        BeanUtils.copyProperties(entityModelDto,user);
        return user;
    }

    @Override
    public List<UserDto> toDtoList(List<User> entityModelList) {
        List<UserDto> userModelDtos = new ArrayList<>();
        for(User model : entityModelList){
            UserDto dto = toDto(model);
            userModelDtos.add(dto);
        }
        return userModelDtos;
    }

    @Override
    public List<User> toEntityList(List<UserDto> entityModelDtoList) {
        List<User> userModels = new ArrayList<>();
        for(UserDto model : entityModelDtoList){
            User modelAdd = toEntity(model);
            userModels.add(modelAdd);
        }
        return userModels;
    }
}
