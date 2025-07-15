package com.tailan.controle_de_despesas.service.user;

import com.tailan.controle_de_despesas.dto.user.LoginRequestDto;
import com.tailan.controle_de_despesas.dto.user.LoginResponseDto;
import com.tailan.controle_de_despesas.dto.user.UserRequestDto;
import com.tailan.controle_de_despesas.dto.user.UserResponseDto;
import com.tailan.controle_de_despesas.entities.user.User;

public interface UserService {

    UserResponseDto register(UserRequestDto userRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    User findByEmail(String email);

}
