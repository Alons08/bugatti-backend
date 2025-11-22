package com.alocode.almacen_service.service.impl;

import com.alocode.almacen_service.dto.request.AlmacenRequest;
import com.alocode.almacen_service.dto.response.AlmacenResponse;
import com.alocode.almacen_service.entity.Almacen;
import com.alocode.almacen_service.repository.AlmacenRepository;
import com.alocode.almacen_service.service.AlmacenService;
import com.alocode.almacen_service.client.UsuarioFeignClient;
import com.alocode.almacen_service.client.dto.UsuarioClientResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlmacenServiceImpl implements AlmacenService {
    private static final Logger log = LoggerFactory.getLogger(AlmacenServiceImpl.class);
    private final AlmacenRepository almacenRepository;
    private final UsuarioFeignClient usuarioFeignClient;

    @Override
    public AlmacenResponse createAlmacen(AlmacenRequest request) {
        log.info("[AlmacenService] Creando almacén con datos: {}", request);
        Almacen almacen = Almacen.builder()
            .idUsuario(request.getIdUsuario())
            .codigoAlmacen(request.getCodigoAlmacen())
            .nombre(request.getNombre())
            .direccion(request.getDireccion())
            .ciudad(request.getCiudad())
            .provincia(request.getProvincia())
            .region(request.getRegion())
            .capacidadMaxima(request.getCapacidadMaxima())
            .telefonoContacto(request.getTelefonoContacto())
            .estado(true)
            .fechaCreacion(java.time.LocalDateTime.now())
            .build();
        almacen = almacenRepository.save(almacen);
        log.info("[AlmacenService] Almacén guardado: {}", almacen);
        return toResponse(almacen);
    }

    @Override
    public AlmacenResponse getAlmacenById(Integer id) {
        return almacenRepository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }

    @Override
    public List<AlmacenResponse> getAllAlmacenes() {
        return almacenRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AlmacenResponse updateAlmacen(Integer id, AlmacenRequest request) {
        return almacenRepository.findById(id)
                .map(almacen -> {
                    almacen.setCodigoAlmacen(request.getCodigoAlmacen());
                    almacen.setNombre(request.getNombre());
                    almacen.setDireccion(request.getDireccion());
                    almacen.setCiudad(request.getCiudad());
                    almacen.setProvincia(request.getProvincia());
                    almacen.setRegion(request.getRegion());
                    almacen.setCapacidadMaxima(request.getCapacidadMaxima());
                    almacen.setTelefonoContacto(request.getTelefonoContacto());
                    almacen = almacenRepository.save(almacen);
                    return toResponse(almacen);
                })
                .orElse(null);
    }

    @Override
    public void deleteAlmacen(Integer id) {
        almacenRepository.deleteById(id);
    }

    private AlmacenResponse toResponse(Almacen almacen) {
        AlmacenResponse response = new AlmacenResponse();
        response.setIdAlmacen(almacen.getIdAlmacen());
        response.setIdUsuario(almacen.getIdUsuario());
        response.setCodigoAlmacen(almacen.getCodigoAlmacen());
        response.setNombre(almacen.getNombre());
        response.setDireccion(almacen.getDireccion());
        response.setCiudad(almacen.getCiudad());
        response.setProvincia(almacen.getProvincia());
        response.setRegion(almacen.getRegion());
        response.setCapacidadMaxima(almacen.getCapacidadMaxima());
        response.setTelefonoContacto(almacen.getTelefonoContacto());
        response.setEstado(almacen.getEstado());
        response.setFechaCreacion(almacen.getFechaCreacion());
        // Obtener datos del usuario desde usuario-service
        UsuarioClientResponse usuario = usuarioFeignClient.getUsuarioById(almacen.getIdUsuario());
        log.info("[AlmacenService] Respuesta Feign usuario: {}", usuario);
        response.setNombreUsuario(usuario != null ? usuario.getNombre() : null);
        return response;
    }
}
