package br.com.project.helpdesk.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String validarToken(String token){
       try {
           Algorithm algoritmo =  Algorithm.HMAC256(secret);
           return JWT.require(algoritmo)
                   .build()
                   .verify(token)
                   .getSubject();
       } catch (JWTVerificationException ex) {
           return null;
       }
    }
}
