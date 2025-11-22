package com.alocode.almacen_service.service.impl;

import com.alocode.almacen_service.dto.request.AlmacenMaterialRequest;
import com.alocode.almacen_service.dto.response.AlmacenMaterialResponse;
import com.alocode.almacen_service.entity.AlmacenMaterial;
import com.alocode.almacen_service.entity.AlmacenMaterialId;
import com.alocode.almacen_service.repository.AlmacenMaterialRepository;
import com.alocode.almacen_service.service.AlmacenMaterialService;
import com.alocode.almacen_service.client.MaterialFeignClient;
import com.alocode.almacen_service.client.dto.MaterialClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlmacenMaterialServiceImpl implements AlmacenMaterialService {
    private final AlmacenMaterialRepository almacenMaterialRepository;
    private final MaterialFeignClient materialFeignClient;

    @Override
    public AlmacenMaterialResponse createOrUpdate(AlmacenMaterialRequest request) {
        AlmacenMaterialId id = new AlmacenMaterialId(request.getIdAlmacen(), request.getIdMaterial());
        AlmacenMaterial almacenMaterial = almacenMaterialRepository.findById(id)
                .orElse(AlmacenMaterial.builder()
                        .idAlmacen(request.getIdAlmacen())
                        .idMaterial(request.getIdMaterial())
                        .build());
        almacenMaterial.setStockActual(request.getStockActual());
        almacenMaterial.setStockMinimo(request.getStockMinimo());
        almacenMaterial = almacenMaterialRepository.save(almacenMaterial);
        return toResponse(almacenMaterial);
    }

    @Override
    public AlmacenMaterialResponse getById(Integer idAlmacen, Integer idMaterial) {
        return almacenMaterialRepository.findById(new AlmacenMaterialId(idAlmacen, idMaterial))
                .map(this::toResponse)
                .orElse(null);
    }

    @Override
    public List<AlmacenMaterialResponse> getByAlmacen(Integer idAlmacen) {
        return almacenMaterialRepository.findAll().stream()
                .filter(am -> am.getIdAlmacen().equals(idAlmacen))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer idAlmacen, Integer idMaterial) {
        almacenMaterialRepository.deleteById(new AlmacenMaterialId(idAlmacen, idMaterial));
    }

    private AlmacenMaterialResponse toResponse(AlmacenMaterial almacenMaterial) {
        AlmacenMaterialResponse response = new AlmacenMaterialResponse();
        response.setIdAlmacen(almacenMaterial.getIdAlmacen());
        response.setIdMaterial(almacenMaterial.getIdMaterial());
        response.setStockActual(almacenMaterial.getStockActual());
        response.setStockMinimo(almacenMaterial.getStockMinimo());
        // Obtener datos del material desde material-service
        MaterialClientResponse material = materialFeignClient.getMaterialById(almacenMaterial.getIdMaterial());
        // Si quieres mostrar el nombre del material en el response, puedes agregarlo aquí
        // response.setNombreMaterial(material != null ? material.getNombre() : null);
        return response;
    }
}
