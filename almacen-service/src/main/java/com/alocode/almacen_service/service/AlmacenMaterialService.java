package com.alocode.almacen_service.service;

import com.alocode.almacen_service.dto.request.AlmacenMaterialRequest;
import com.alocode.almacen_service.dto.response.AlmacenMaterialResponse;
import java.util.List;

public interface AlmacenMaterialService {
    AlmacenMaterialResponse createOrUpdate(AlmacenMaterialRequest request);
    AlmacenMaterialResponse getById(Integer idAlmacen, Integer idMaterial);
    List<AlmacenMaterialResponse> getByAlmacen(Integer idAlmacen);
    void delete(Integer idAlmacen, Integer idMaterial);
}
