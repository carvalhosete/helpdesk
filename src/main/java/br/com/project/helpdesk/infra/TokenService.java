package br.com.project.helpdesk.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private String secret = "Helpdesk@Secret!2026_JWT_GoDrink";

    public String validarToken(String token){
       Algorithm algoritmo =  Algorithm.HMAC256(secret);

       return JWT.require(algoritmo).build().verify(token).getSubject();
    }
}
