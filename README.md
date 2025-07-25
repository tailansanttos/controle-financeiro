# Controle de Despesas API
Este projeto consiste em uma API RESTful para um sistema de controle financeiro pessoal, desenvolvida com Spring Boot. O objetivo é permitir que os usuários gerenciem suas finanças, registrando receitas e despesas, categorizando transações e acompanhando saldos.

## Tecnologias Utilizadas
- *Linguagem:* Java
- *Framework:* Spring boot
- *Segurança:* Spring Security (JWT para autenticação)
- *Persistência:* Spring Data JPA
- *Banco de Dados:* PostgreSQL
- *Geração/Validação de JWT:* Auth0 Java JWT
- *Build Tool:* Maven

## Status atual do projeto
Até o momento, as seguintes funcionalidades foram implementados e configurados:
1. Autenticação e Autorização de usuários com JWT
- *Registro de Usuários:* Endpoint para usuários se cadastrarem.
- *Login de Usuários:* Endpoint para usuários já cadastrados fazerem logi e receberem um token JWT
- *Geração e Validação de JWT:* Implementado um serviço para criar tokens com base nas informações do usuário, e validar.
- *Criptografia de Senhas:* Utilizaçãi de BCryptPasswordEncoder para armazenar senhas de forma segura.

