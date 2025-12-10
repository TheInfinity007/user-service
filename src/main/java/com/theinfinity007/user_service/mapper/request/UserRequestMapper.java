package com.theinfinity007.user_service.mapper.request;

import com.theinfinity007.user_service.dto.UserCreateRequestDto;
import com.theinfinity007.user_service.dto.UserUpdateRequestDto;
import com.theinfinity007.user_service.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRequestMapper {

    private final PasswordEncoder passwordEncoder;

    public User populate(UserCreateRequestDto userCreateRequestDto){
        User user = new User();

        String password = passwordEncoder.encode(userCreateRequestDto.getPassword());

        user.setEmail(userCreateRequestDto.getEmail());
        user.setPassword(password);
        user.setFirstName(userCreateRequestDto.getFirstName());
        user.setLastName(userCreateRequestDto.getLastName());
        user.setBirthday(userCreateRequestDto.getBirthday());
        user.setGender(userCreateRequestDto.getGender());
        user.setTitle(userCreateRequestDto.getTitle());

        return user;
    }

    public User populate(UserUpdateRequestDto source, User target){

        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setBirthday(source.getBirthday());
        target.setGender(source.getGender());
        target.setTitle(source.getTitle());

        return target;
    }
}
