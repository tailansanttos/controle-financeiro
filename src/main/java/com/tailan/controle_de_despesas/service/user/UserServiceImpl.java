package com.tailan.controle_de_despesas.service.user;

import com.tailan.controle_de_despesas.config.JwtService;
import com.tailan.controle_de_despesas.dto.user.LoginRequestDto;
import com.tailan.controle_de_despesas.dto.user.LoginResponseDto;
import com.tailan.controle_de_despesas.dto.user.UserRequestDto;
import com.tailan.controle_de_despesas.dto.user.UserResponseDto;
import com.tailan.controle_de_despesas.entities.user.User;
import com.tailan.controle_de_despesas.entities.user.UserRole;
import com.tailan.controle_de_despesas.mapper.usuario.UserMapper;
import com.tailan.controle_de_despesas.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, JwtService tokenService, AuthenticationManager authenticationManager, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDto register(UserRequestDto userRequestDto) {
        String email = userRequestDto.email();

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new RuntimeException("Usuario com esse email já cadastrado");
        }
        String password = userRequestDto.password();

        String passwordEncrypte = new BCryptPasswordEncoder().encode(password);

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncrypte);
        newUser.setCpf(userRequestDto.cpf());
        newUser.setName(userRequestDto.name());
        newUser.setCreationDate(LocalDateTime.now());
        newUser.setRole(UserRole.USER);

        User savedUser = userRepository.save(newUser);
        return userMapper.entityToDto(savedUser);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password());

        var authentication = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) authentication.getPrincipal());

        return new LoginResponseDto(token);
    }
}
