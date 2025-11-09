package com.theinfinity007.user_service.mapper.request;

import com.theinfinity007.user_service.dto.UserCreateRequestDto;
import com.theinfinity007.user_service.entity.User;

public class UserRequestMapper {
    public static User populate(UserCreateRequestDto userCreateRequestDto){
        User user = new User();

        user.setEmail(userCreateRequestDto.getEmail());
        user.setPassword(userCreateRequestDto.getPassword());
        user.setFirstName(userCreateRequestDto.getFirstName());
        user.setLastName(userCreateRequestDto.getLastName());
        user.setBirthday(userCreateRequestDto.getBirthday());
        user.setGender(userCreateRequestDto.getGender());
        user.setTitle(userCreateRequestDto.getTitle());

        return user;
    }
}
