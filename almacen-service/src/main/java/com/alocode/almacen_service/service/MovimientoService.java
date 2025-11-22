package com.alocode.almacen_service.service;

import com.alocode.almacen_service.dto.request.MovimientoRequest;
import com.alocode.almacen_service.dto.response.MovimientoResponse;
import java.util.List;

public interface MovimientoService {
    MovimientoResponse createMovimiento(MovimientoRequest request);
    MovimientoResponse getMovimientoById(Integer id);
    List<MovimientoResponse> getMovimientosByAlmacen(Integer idAlmacen);
    void deleteMovimiento(Integer id);
}
