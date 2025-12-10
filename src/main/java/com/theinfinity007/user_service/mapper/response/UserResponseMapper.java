package com.theinfinity007.user_service.mapper.response;

import com.theinfinity007.user_service.dto.UserResponseDto;
import com.theinfinity007.user_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {
    public static UserResponseDto populate(User user){
        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setGender(user.getGender());
        userResponseDto.setBirthday(user.getBirthday());
        userResponseDto.setTitle(user.getTitle());
        userResponseDto.setCreatedAt(user.getCreatedAt());
        userResponseDto.setModifiedAt(user.getModifiedAt());

        return userResponseDto;
    }
}
