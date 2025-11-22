package com.alocode.material_service.service;

import com.alocode.material_service.entity.TipoMaterial;
import com.alocode.material_service.dto.request.TipoMaterialRequest;
import com.alocode.material_service.dto.response.TipoMaterialResponse;
import com.alocode.material_service.repository.TipoMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoMaterialServiceImpl implements TipoMaterialService {
    @Autowired
    private TipoMaterialRepository tipoMaterialRepository;

    @Override
    public List<TipoMaterialResponse> findAll() {
        return tipoMaterialRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    public Optional<TipoMaterialResponse> findById(Integer id) {
        return tipoMaterialRepository.findById(id).map(this::toResponse);
    }

    @Override
    public TipoMaterialResponse save(TipoMaterialRequest request) {
        TipoMaterial tipoMaterial = toEntity(request);
        TipoMaterial saved = tipoMaterialRepository.save(tipoMaterial);
        return toResponse(saved);
    }

    @Override
    public TipoMaterialResponse update(Integer id, TipoMaterialRequest request) {
        Optional<TipoMaterial> existing = tipoMaterialRepository.findById(id);
        if (existing.isPresent()) {
            TipoMaterial tipoMaterial = toEntity(request);
            tipoMaterial.setIdTipo(id);
            TipoMaterial updated = tipoMaterialRepository.save(tipoMaterial);
            return toResponse(updated);
        }
        return null;
    }
    private TipoMaterial toEntity(TipoMaterialRequest request) {
        TipoMaterial tipoMaterial = new TipoMaterial();
        tipoMaterial.setNombreTipo(request.getNombreTipo());
        return tipoMaterial;
    }

    private TipoMaterialResponse toResponse(TipoMaterial tipoMaterial) {
        TipoMaterialResponse response = new TipoMaterialResponse();
        response.setIdTipo(tipoMaterial.getIdTipo());
        response.setNombreTipo(tipoMaterial.getNombreTipo());
        return response;
    }

    @Override
    public void delete(Integer id) {
        tipoMaterialRepository.deleteById(id);
    }
}
