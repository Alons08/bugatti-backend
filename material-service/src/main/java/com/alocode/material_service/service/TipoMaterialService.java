package com.alocode.material_service.service;

import com.alocode.material_service.dto.request.TipoMaterialRequest;
import com.alocode.material_service.dto.response.TipoMaterialResponse;
import java.util.List;
import java.util.Optional;

public interface TipoMaterialService {
    List<TipoMaterialResponse> findAll();
    Optional<TipoMaterialResponse> findById(Integer id);
    TipoMaterialResponse save(TipoMaterialRequest request);
    TipoMaterialResponse update(Integer id, TipoMaterialRequest request);
    void delete(Integer id);
}
