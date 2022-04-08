package br.edu.utfpr.parkineasy.security;

import br.edu.utfpr.parkineasy.exception.ParkineasyException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    private static final String ALIAS = "parkineasy";
    private static final String SECRET = "secret";
    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream inputStream = getClass().getResourceAsStream("/parkineasy.jks");
            keyStore.load(inputStream, SECRET.toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new ParkineasyException("Ocorreu uma excecao ao carregar a keystore");
        }
    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
            .setSubject(principal.getUsername())
            .signWith(getPrivateKey())
            .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey(ALIAS, SECRET.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new ParkineasyException("Ocorreu uma excecao ao recuperar a chave privada da keystore");
        }
    }

    public boolean validateToken(String jwt) {
        Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate(ALIAS).getPublicKey();
        } catch (KeyStoreException e) {
            throw new ParkineasyException("Ocorreu uma excecao ao recuperar a chave publica da keystore");
        }
    }

    public String getUsernameFromJwt(String jwt) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt).getBody();
        return claims.getSubject();
    }
}
