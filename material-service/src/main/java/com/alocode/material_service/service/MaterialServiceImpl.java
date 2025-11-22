package com.alocode.material_service.service;

import com.alocode.material_service.entity.Material;
import com.alocode.material_service.entity.TipoMaterial;
import com.alocode.material_service.dto.request.MaterialRequest;
import com.alocode.material_service.dto.response.MaterialResponse;
import com.alocode.material_service.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private com.alocode.material_service.repository.TipoMaterialRepository tipoMaterialRepository;

    @Override
    public List<MaterialResponse> findAll() {
        return materialRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    public Optional<MaterialResponse> findById(Integer id) {
        return materialRepository.findById(id).map(this::toResponse);
    }

    @Override
    public MaterialResponse save(MaterialRequest request) {
        if (request.getIdTipo() != null) {
            boolean exists = tipoMaterialRepository.existsById(request.getIdTipo());
            if (!exists) {
                throw new IllegalArgumentException("El tipo de material con id " + request.getIdTipo() + " no existe.");
            }
        }
        Material material = toEntity(request);
        Material saved = materialRepository.save(material);
        return toResponse(saved);
    }

    @Override
    public MaterialResponse update(Integer id, MaterialRequest request) {
        Optional<Material> existing = materialRepository.findById(id);
        if (existing.isPresent()) {
            Material material = toEntity(request);
            material.setIdMaterial(id);
            Material updated = materialRepository.save(material);
            return toResponse(updated);
        }
        return null;
    }

    private Material toEntity(MaterialRequest request) {
        Material material = new Material();
        material.setNombre(request.getNombre());
        material.setDescripcion(request.getDescripcion());
        material.setEstado(request.getEstado());
        material.setUnidadMedida(request.getUnidadMedida());
        if (request.getIdTipo() != null) {
            TipoMaterial tipoMaterial = tipoMaterialRepository.findById(request.getIdTipo()).orElse(null);
            material.setTipoMaterial(tipoMaterial);
        }
        return material;
    }

    private MaterialResponse toResponse(Material material) {
        MaterialResponse response = new MaterialResponse();
        response.setIdMaterial(material.getIdMaterial());
        if (material.getTipoMaterial() != null) {
            response.setIdTipo(material.getTipoMaterial().getIdTipo());
            response.setNombreTipo(material.getTipoMaterial().getNombreTipo());
        }
        response.setNombre(material.getNombre());
        response.setDescripcion(material.getDescripcion());
        response.setEstado(material.getEstado());
        response.setUnidadMedida(material.getUnidadMedida());
        return response;
    }

    @Override
    public void delete(Integer id) {
        materialRepository.deleteById(id);
    }
}
