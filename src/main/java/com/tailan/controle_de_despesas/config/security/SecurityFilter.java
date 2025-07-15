package com.tailan.controle_de_despesas.config.security;

import com.tailan.controle_de_despesas.config.token.TokenService;
import com.tailan.controle_de_despesas.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserRepository  userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();

        //URLS PUBLICAS IGNORA O FILTRO JWT PARA ROTAS ABERTAS
        //Garante que as requisições públicas não tentem validar um token que não existe.

        if ((requestMethod.equals("POST") && requestURI.equals("/api/auth/register") || requestURI.equals("/api/auth/login"))){
            filterChain.doFilter(request,response);
            return;
        }

        //ROTAS PROTEGIDAS, TENTA VALIDAR O TOKEN PARA AS OUTRAS ROTAS
        var token =  this.recoverToken(request); //pega o token do cabeçalho
        if (token != null ){
            try{
                var login = tokenService.validateToken(token);
                UserDetails user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("Usuario não encontrado para o token fornecido"));

                //cria um objeto de autenticação e define no SecurityContextHolder

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }catch (Exception e){
                //caso o token  seja invalido, loga o erro
                System.err.println("Erro ao validar token " + e.getMessage());
            }
            //CASO NAO PASSEM UM TOKEN NA REQUISIÇÃO PROTEGIDA.
        }else System.out.println("Requisição protegiado, passe um token.");
    }

    //PEGA O TOKEN  JWT DO CABEÇALHO "AUTHORIZATION"
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
