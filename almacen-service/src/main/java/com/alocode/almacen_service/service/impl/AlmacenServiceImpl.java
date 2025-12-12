package com.alocode.almacen_service.service.impl;

import com.alocode.almacen_service.dto.request.AlmacenRequest;
import com.alocode.almacen_service.dto.response.AlmacenResponse;
import com.alocode.almacen_service.dto.response.EstadisticasResponse;
import com.alocode.almacen_service.dto.response.MovimientoRecienteResponse;
import com.alocode.almacen_service.entity.Almacen;
import com.alocode.almacen_service.entity.Movimiento;
import com.alocode.almacen_service.entity.MovimientoDetalle;
import com.alocode.almacen_service.repository.AlmacenRepository;
import com.alocode.almacen_service.repository.AlmacenMaterialRepository;
import com.alocode.almacen_service.repository.MovimientoRepository;
import com.alocode.almacen_service.repository.MovimientoDetalleRepository;
import com.alocode.almacen_service.service.AlmacenService;
import com.alocode.almacen_service.client.UsuarioFeignClient;
import com.alocode.almacen_service.client.MaterialFeignClient;
import com.alocode.almacen_service.client.dto.UsuarioClientResponse;
import com.alocode.almacen_service.client.dto.MaterialClientResponse;
import com.alocode.almacen_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlmacenServiceImpl implements AlmacenService {
    private static final Logger log = LoggerFactory.getLogger(AlmacenServiceImpl.class);
    private final AlmacenRepository almacenRepository;
    private final AlmacenMaterialRepository almacenMaterialRepository;
    private final MovimientoRepository movimientoRepository;
    private final MovimientoDetalleRepository movimientoDetalleRepository;
    private final UsuarioFeignClient usuarioFeignClient;
    private final MaterialFeignClient materialFeignClient;
    private final JwtUtil jwtUtil;

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
                    almacen.setIdUsuario(request.getIdUsuario());
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

    @Override
    public EstadisticasResponse getEstadisticas() {
        Integer stockTotal = almacenMaterialRepository.getTotalStock();
        Integer materialesAgotados = almacenMaterialRepository.getMaterialesAgotados();
        Integer movimientosHoy = movimientoRepository.getMovimientosDelDia(LocalDate.now());
        
        return EstadisticasResponse.builder()
            .stockTotal(stockTotal != null ? stockTotal : 0)
            .materialesAgotados(materialesAgotados != null ? materialesAgotados : 0)
            .movimientosHoy(movimientosHoy != null ? movimientosHoy : 0)
            .build();
    }

    @Override
    public List<MovimientoRecienteResponse> getMovimientosRecientes(Integer limite) {
        List<Movimiento> movimientos = movimientoRepository.findMovimientosRecientes();
        
        // Limitar resultados si se especifica
        if (limite != null && limite > 0) {
            movimientos = movimientos.stream().limit(limite).collect(Collectors.toList());
        }
        
        List<MovimientoRecienteResponse> result = new ArrayList<>();
        
        for (Movimiento movimiento : movimientos) {
            // Obtener detalles del movimiento
            List<MovimientoDetalle> detalles = movimientoDetalleRepository.findByIdMovimiento(movimiento.getIdMovimiento());
            
            for (MovimientoDetalle detalle : detalles) {
                try {
                    // Obtener información del material
                    MaterialClientResponse material = materialFeignClient.getMaterialById(detalle.getIdMaterial());
                    
                    MovimientoRecienteResponse item = MovimientoRecienteResponse.builder()
                        .nombreMaterial(material != null ? material.getNombre() : "Material desconocido")
                        .tipoMovimiento(movimiento.getTipoMovimiento().name())
                        .cantidad(detalle.getCantidad())
                        .hora(movimiento.getFechaHora())
                        .build();
                    
                    result.add(item);
                } catch (Exception e) {
                    log.warn("[AlmacenService] Error obteniendo material {}: {}", detalle.getIdMaterial(), e.getMessage());
                    
                    // Agregar con información limitada si falla el servicio
                    MovimientoRecienteResponse item = MovimientoRecienteResponse.builder()
                        .nombreMaterial("Material ID: " + detalle.getIdMaterial())
                        .tipoMovimiento(movimiento.getTipoMovimiento().name())
                        .cantidad(detalle.getCantidad())
                        .hora(movimiento.getFechaHora())
                        .build();
                    
                    result.add(item);
                }
            }
        }
        
        return result;
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

    @Override
    public List<AlmacenResponse> getAlmacenesByUsuario(String email) {
        log.info("[AlmacenService] Obteniendo almacenes para usuario con email: {}", email);
        UsuarioClientResponse usuario = usuarioFeignClient.getUsuarioByEmail(email);
        if (usuario == null) {
            log.warn("[AlmacenService] Usuario no encontrado para email: {}", email);
            return List.of();
        }
        List<Almacen> almacenes = almacenRepository.findByIdUsuario(usuario.getId());
        return almacenes.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
