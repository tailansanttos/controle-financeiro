package com.tailan.controle_de_despesas.controllers;

import com.tailan.controle_de_despesas.dto.api.ApiResponseDto;
import com.tailan.controle_de_despesas.dto.user.LoginRequestDto;
import com.tailan.controle_de_despesas.dto.user.LoginResponseDto;
import com.tailan.controle_de_despesas.dto.user.UserRequestDto;
import com.tailan.controle_de_despesas.dto.user.UserResponseDto;
import com.tailan.controle_de_despesas.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto> register(@RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.register(userRequestDto);
        return ResponseEntity.ok(new ApiResponseDto("Usúario registrado com sucesso.", userResponseDto));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody @Valid LoginRequestDto userLoginRequest){
        LoginResponseDto userLoginResponse = userService.login(userLoginRequest);
        return ResponseEntity.ok(new ApiResponseDto("Login efetuado com sucesso.", userLoginResponse));


    }
}
