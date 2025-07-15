package com.tailan.controle_de_despesas.mapper.usuario;

import com.tailan.controle_de_despesas.dto.user.UserResponseDto;
import com.tailan.controle_de_despesas.entities.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto entityToDto(User user) {
      return new UserResponseDto(
              user.getId(),
              user.getName(),
              user.getEmail(),
              user.getCreationDate(),
              user.getRole()
      );
    }


}
