package com.santander.genai.etl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.public-key-pem}")
    private String publicKeyPem;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/clientes/**").hasAuthority("SCOPE_api:read")
                .requestMatchers(HttpMethod.POST, "/api/clientes/**").hasAuthority("SCOPE_api:write")
                .requestMatchers(HttpMethod.PUT, "/api/clientes/**").hasAuthority("SCOPE_api:write")
                .requestMatchers(HttpMethod.DELETE, "/api/clientes/**").hasAuthority("SCOPE_api:write")
                .requestMatchers("/api/mensagens/**").hasAnyAuthority("SCOPE_api:read","SCOPE_api:write")
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Usa chave pública PEM (PKCS#8). Em produção, use JWK Set (issuer/.well-known/jwks.json)
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(parsePublicKey(publicKeyPem)).build();
        decoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(issuer));
        return decoder;
    }

    private RSAPublicKey parsePublicKey(String pem) {
        String clean = pem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(clean);
        return (RSAPublicKey) KeyUtils.publicKey(decoded);
    }
}
