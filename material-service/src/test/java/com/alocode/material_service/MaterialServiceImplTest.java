package com.alocode.material_service;

import com.alocode.material_service.dto.request.MaterialRequest;
import com.alocode.material_service.dto.response.MaterialResponse;
import com.alocode.material_service.entity.Material;
import com.alocode.material_service.entity.TipoMaterial;
import com.alocode.material_service.repository.MaterialRepository;
import com.alocode.material_service.repository.TipoMaterialRepository;
import com.alocode.material_service.service.MaterialServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MaterialServiceImplTest {
    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private TipoMaterialRepository tipoMaterialRepository;

    @InjectMocks
    private MaterialServiceImpl materialService;

    public MaterialServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllReturnsEmptyList() {
        when(materialRepository.findAll()).thenReturn(Collections.emptyList());
        List<MaterialResponse> result = materialService.findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByIdReturnsMaterial() {
        Material material = new Material();
        material.setIdMaterial(1);
        material.setNombre("Madera");
        material.setDescripcion("Material de construcción");
        material.setEstado(true);
        material.setUnidadMedida("kg");
        TipoMaterial tipo = new TipoMaterial(2, "Orgánico");
        material.setTipoMaterial(tipo);
        when(materialRepository.findById(1)).thenReturn(java.util.Optional.of(material));
        var result = materialService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("Madera", result.get().getNombre());
        assertEquals("Orgánico", result.get().getNombreTipo());
    }

    @Test
    void testSaveMaterialThrowsIfTipoNotExists() {
        MaterialRequest req = new MaterialRequest();
        req.setIdTipo(99);
        req.setNombre("Acero");
        req.setDescripcion("Metal");
        req.setEstado(true);
        req.setUnidadMedida("kg");
        when(tipoMaterialRepository.existsById(99)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> materialService.save(req));
    }

    @Test
    void testDeleteMaterial() {
        doNothing().when(materialRepository).deleteById(1);
        materialService.delete(1);
        verify(materialRepository, times(1)).deleteById(1);
    }
}
