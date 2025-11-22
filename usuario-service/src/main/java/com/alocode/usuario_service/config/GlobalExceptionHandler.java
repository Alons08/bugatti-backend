package com.alocode.usuario_service.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;

import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*  Manejador global de excepciones
    Todas las respuestas de error se normalizan aquí y se devuelven con mensajes en español */
    
@ControllerAdvice
public class GlobalExceptionHandler {

    /*  Códigos de error HTTP devueltos:
            * 400 BAD REQUEST:
            *   - MethodArgumentNotValidException
            *   - BindException
            *   - ConstraintViolationException
            *   - HttpMessageNotReadableException
            *   - IllegalArgumentException
            * 404 NOT FOUND:
            *   - IllegalStateException
            * 401 UNAUTHORIZED:
            *   - AuthenticationException
            * 403 FORBIDDEN:
            *   - AccessDeniedException
            * 409 CONFLICT:
            *   - DataIntegrityViolationException
            * 500 INTERNAL SERVER ERROR:
            *   - Exception (cualquier otro error) 
    */


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        // 400 BAD REQUEST - Construir lista de errores por campo (campo -> mensaje)
        List<Map<String, String>> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            Map<String, String> e = new HashMap<>();
            e.put("campo", err.getField());
            e.put("mensaje", err.getDefaultMessage());
            errors.add(e);
        });
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Validación fallida");
        body.put("detalles", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, Object>> handleBindException(BindException ex) {
        // 400 BAD REQUEST - Errores de enlace/parseo de parámetros
        List<Map<String, String>> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            Map<String, String> e = new HashMap<>();
            e.put("campo", err.getField());
            e.put("mensaje", err.getDefaultMessage());
            errors.add(e);
        });
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Fallo de enlace");
        body.put("detalles", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        // 400 BAD REQUEST - Errores de validación de constraints (por ejemplo validaciones en parámetros)
        List<Map<String, String>> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(cv -> {
            Map<String, String> e = new HashMap<>();
            e.put("ruta", cv.getPropertyPath().toString());
            e.put("mensaje", cv.getMessage());
            errors.add(e);
        });
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Validación fallida");
        body.put("detalles", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleNotReadable(HttpMessageNotReadableException ex) {
        // 400 BAD REQUEST - JSON mal formado en la petición
        Map<String, Object> body = new HashMap<>();
        body.put("error", "JSON mal formado");
        body.put("mensaje", ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        // 409 CONFLICT - Violación de integridad de datos (por ejemplo duplicados en BD)
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Violación de integridad de datos");
        body.put("mensaje", ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        // 400 BAD REQUEST - Petición con parámetros inválidos
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Petición incorrecta");
        body.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(IllegalStateException ex) {
        // 404 NOT FOUND - Recurso no encontrado
        Map<String, Object> body = new HashMap<>();
        body.put("error", "No encontrado");
        body.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthentication(AuthenticationException ex) {
        // 401 UNAUTHORIZED - Autenticación fallida
        Map<String, Object> body = new HashMap<>();
        body.put("error", "No autorizado");
        body.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        // 403 FORBIDDEN - Acceso denegado (falta permiso)
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Prohibido");
        body.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        // 500 INTERNAL SERVER ERROR - Error interno inesperado
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Error interno del servidor");
        body.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
