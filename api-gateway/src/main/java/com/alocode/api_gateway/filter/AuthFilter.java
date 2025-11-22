
package com.alocode.api_gateway.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import io.jsonwebtoken.io.Decoders;

@Component
public class AuthFilter implements WebFilter {

	private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

	@Value("${jwt.secret}")
	private String secretKey;


	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String path = request.getPath().value();

		logger.info("[AuthFilter] Request path: {}", path);

		// Solo proteger rutas de habitacion-service
		if (isAdminPath(path)) {
			logger.info("[AuthFilter] Ruta protegida, validando JWT...");
			String authHeader = request.getHeaders().getFirst("Authorization");
			logger.info("[AuthFilter] Authorization header: {}", authHeader);
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				logger.warn("[AuthFilter] No se encontró el header Authorization o no es Bearer");
				return this.unauthorized(exchange.getResponse());
			}
			String token = authHeader.substring(7);
			Claims claims;
			try {
				claims = Jwts.parserBuilder()
						.setSigningKey(getSigningKey())
						.build()
						.parseClaimsJws(token)
						.getBody();
				logger.info("[AuthFilter] Claims del token: {}", claims);
			} catch (Exception e) {
				logger.error("[AuthFilter] Error al validar el token: {}", e.getMessage());
				return this.unauthorized(exchange.getResponse());
			}
			// El rol puede estar en el claim "roles" o "authorities"
			Object rolesObj = claims.get("roles");
			if (rolesObj == null) rolesObj = claims.get("authorities");
			logger.info("[AuthFilter] Valor de roles/authorities: {}", rolesObj);
			if (rolesObj == null || !rolesObj.toString().contains("ADMINISTRATOR")) {
				logger.warn("[AuthFilter] El usuario no tiene el rol ADMINISTRATOR");
				return this.forbidden(exchange.getResponse());
			}
			logger.info("[AuthFilter] Acceso permitido para ADMINISTRATOR");
		}
		return chain.filter(exchange);
	}

	private boolean isAdminPath(String path) {
		// Aquí protegemos las rutas que considere, pero de momento solo las de usuario son exclusivas
		return false;
	}

	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private Mono<Void> unauthorized(ServerHttpResponse response) {
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		return response.setComplete();
	}

	private Mono<Void> forbidden(ServerHttpResponse response) {
		response.setStatusCode(HttpStatus.FORBIDDEN);
		return response.setComplete();
	}
}
