package com.alocode.almacen_service.service;

import com.alocode.almacen_service.dto.request.AlmacenRequest;
import com.alocode.almacen_service.dto.response.AlmacenResponse;
import com.alocode.almacen_service.dto.response.EstadisticasResponse;
import com.alocode.almacen_service.dto.response.MovimientoRecienteResponse;
import java.util.List;

public interface AlmacenService {
    AlmacenResponse createAlmacen(AlmacenRequest request);
    AlmacenResponse getAlmacenById(Integer id);
    List<AlmacenResponse> getAllAlmacenes();
    AlmacenResponse updateAlmacen(Integer id, AlmacenRequest request);
    void deleteAlmacen(Integer id);
    EstadisticasResponse getEstadisticas();
    List<MovimientoRecienteResponse> getMovimientosRecientes(Integer limite);
}
