package com.tailan.controle_de_despesas.service.user;

import com.tailan.controle_de_despesas.config.token.TokenService;
import com.tailan.controle_de_despesas.dto.user.LoginRequestDto;
import com.tailan.controle_de_despesas.dto.user.LoginResponseDto;
import com.tailan.controle_de_despesas.dto.user.UserRequestDto;
import com.tailan.controle_de_despesas.dto.user.UserResponseDto;
import com.tailan.controle_de_despesas.entities.user.User;
import com.tailan.controle_de_despesas.entities.user.UserRole;
import com.tailan.controle_de_despesas.mapper.usuario.UserMapper;
import com.tailan.controle_de_despesas.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, TokenService tokenService, AuthenticationManager authenticationManager, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public UserResponseDto register(UserRequestDto userRequestDto) {
        String email = userRequestDto.email();
        Optional<User> existeUser = userRepository.findByEmail(email);
        if (existeUser.isPresent()) {
            throw new IllegalArgumentException("Usuario com esse email já cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(userRequestDto.password());

        User user = new User();
        user.setEmail(email);
        user.setPassword(encryptedPassword);
        user.setRole(UserRole.USER);
        user.setCpf(userRequestDto.cpf());
        user.setName(userRequestDto.name());
        user.setCreationDate(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        return userMapper.entityToDto(savedUser);
    }

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        //CRIA UM TOKEN DE AUTENTICAÇÃO COM AS CREDENCIAIS DO USUARIO
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password());

        //AUTENTICA O USUARIO USANDO O AUTHENTICATIONMANAGER

        var authentication = authenticationManager.authenticate(usernamePassword);

        //SE A AUTENTICAÇÃO FOR BEM SUCESSIDA, GERA UM JWT

        var token = tokenService.genereteToken((User) authentication.getPrincipal());

        return new LoginResponseDto(token);
    }

    @Override
    public User findByEmail(String email) {
       return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado."));
    }
}
