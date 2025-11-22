package com.alocode.almacen_service.service;

import com.alocode.almacen_service.dto.request.AlmacenRequest;
import com.alocode.almacen_service.dto.response.AlmacenResponse;
import java.util.List;

public interface AlmacenService {
    AlmacenResponse createAlmacen(AlmacenRequest request);
    AlmacenResponse getAlmacenById(Integer id);
    List<AlmacenResponse> getAllAlmacenes();
    AlmacenResponse updateAlmacen(Integer id, AlmacenRequest request);
    void deleteAlmacen(Integer id);
}
