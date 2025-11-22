package com.alocode.almacen_service.service.impl;

import com.alocode.almacen_service.dto.request.MovimientoRequest;
import com.alocode.almacen_service.dto.response.MovimientoResponse;
import com.alocode.almacen_service.dto.response.MovimientoDetalleResponse;
import com.alocode.almacen_service.entity.Movimiento;
import com.alocode.almacen_service.entity.MovimientoDetalle;
import com.alocode.almacen_service.entity.TipoMovimiento;
import com.alocode.almacen_service.repository.MovimientoRepository;
import com.alocode.almacen_service.repository.MovimientoDetalleRepository;
import com.alocode.almacen_service.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {
    private final MovimientoRepository movimientoRepository;
    private final MovimientoDetalleRepository movimientoDetalleRepository;

    @Override
    public MovimientoResponse createMovimiento(MovimientoRequest request) {
        Movimiento movimiento = Movimiento.builder()
            .idAlmacen(request.getIdAlmacen())
            .idUsuario(request.getIdUsuario())
            .tipoMovimiento(TipoMovimiento.valueOf(request.getTipoMovimiento()))
            .fechaHora(java.time.LocalDateTime.now())
            .descripcion(request.getDescripcion())
            .referencia(request.getReferencia())
            .build();
        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);
        List<MovimientoDetalle> detalles = request.getDetalles().stream()
            .map(det -> MovimientoDetalle.builder()
                .idMovimiento(movimientoGuardado.getIdMovimiento())
                .idMaterial(det.getIdMaterial())
                .cantidad(det.getCantidad())
                .build())
            .map(movimientoDetalleRepository::save)
            .collect(Collectors.toList());
        return toResponse(movimientoGuardado, detalles);
    }

    @Override
    public MovimientoResponse getMovimientoById(Integer id) {
        return movimientoRepository.findById(id)
                .map(movimiento -> {
                    List<MovimientoDetalle> detalles = movimientoDetalleRepository.findAll().stream()
                            .filter(md -> md.getIdMovimiento().equals(movimiento.getIdMovimiento()))
                            .collect(Collectors.toList());
                    return toResponse(movimiento, detalles);
                })
                .orElse(null);
    }

    @Override
    public List<MovimientoResponse> getMovimientosByAlmacen(Integer idAlmacen) {
        return movimientoRepository.findAll().stream()
                .filter(mov -> mov.getIdAlmacen().equals(idAlmacen))
                .map(movimiento -> {
                    List<MovimientoDetalle> detalles = movimientoDetalleRepository.findAll().stream()
                            .filter(md -> md.getIdMovimiento().equals(movimiento.getIdMovimiento()))
                            .collect(Collectors.toList());
                    return toResponse(movimiento, detalles);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMovimiento(Integer id) {
        movimientoRepository.deleteById(id);
        // También podrías eliminar los detalles asociados si lo deseas
    }

    private MovimientoResponse toResponse(Movimiento movimiento, List<MovimientoDetalle> detalles) {
        MovimientoResponse response = new MovimientoResponse();
        response.setIdMovimiento(movimiento.getIdMovimiento());
        response.setIdAlmacen(movimiento.getIdAlmacen());
        response.setIdUsuario(movimiento.getIdUsuario());
        response.setTipoMovimiento(movimiento.getTipoMovimiento().name());
        response.setFechaHora(movimiento.getFechaHora());
        response.setDescripcion(movimiento.getDescripcion());
        response.setReferencia(movimiento.getReferencia());
        response.setDetalles(detalles.stream().map(this::toDetalleResponse).collect(Collectors.toList()));
        return response;
    }

    private MovimientoDetalleResponse toDetalleResponse(MovimientoDetalle detalle) {
        MovimientoDetalleResponse response = new MovimientoDetalleResponse();
        response.setIdDetalle(detalle.getIdDetalle());
        response.setIdMaterial(detalle.getIdMaterial());
        response.setCantidad(detalle.getCantidad());
        return response;
    }
}
