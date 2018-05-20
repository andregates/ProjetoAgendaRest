/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.Credencial;

import java.security.Key;
import java.util.Calendar;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import dao.implementations.CredencialDaoImpl;
import exceptions.CustomNotAuthorizedException;

/**
 *
 * @author Taniro
 */
public class TokenUtil {
    
    public static String criaToken(String username) {                
        
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS384;
        
        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("35tdsxz");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
 
        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .claim("usuario", username)
                .claim("create", Calendar.getInstance().getTime())
                .signWith(signatureAlgorithm, signingKey);//Token completo e compactado
        
        String compact = builder.compact();
        
        return compact;
    }
    
    public static Credencial validaToken(String token) throws Exception {
        // Verifica se o token existe no banco de dados, caso não existir lançar uma exceção
    	
    	try {
    		CredencialDaoImpl c = new CredencialDaoImpl();
        	Credencial c1 = c.buscarPorToken(token);
			
			if(c1 != null) {
				if(c1.getToken() == null) {
						throw new Exception();
				} else {
					//refreshToken(credenciais);
					return c1;
				}
			} else {
				throw new CustomNotAuthorizedException("Token n�o encontrado.");
			}
					
		} finally {
			// TODO: handle finally clause
}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }
}
