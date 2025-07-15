package com.tailan.controle_de_despesas.config.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.tailan.controle_de_despesas.entities.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${jwt.secret.key}")
    private String secret;


    //GERA UM TOKEN PARA O USUARIO FORNECIDO
    //O TOKEN TEM O EMAIL DO USUAIRO COMO "SUBJECT" E UMA DATA DE EXPIRAÇÃO

    public String genereteToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("controle-despesas") //emissor do token, nome da api
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }



    private Instant generateExpirationDate(){
        // Token expira em 2 horas. Ajuste o ZoneOffset para o seu fuso horário de servidor/aplicação.
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
