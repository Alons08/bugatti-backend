package com.alocode.material_service;

import com.alocode.material_service.dto.request.TipoMaterialRequest;
import com.alocode.material_service.dto.response.TipoMaterialResponse;
import com.alocode.material_service.entity.TipoMaterial;
import com.alocode.material_service.repository.TipoMaterialRepository;
import com.alocode.material_service.service.TipoMaterialServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TipoMaterialServiceImplTest {
    @Mock
    private TipoMaterialRepository tipoMaterialRepository;
    @InjectMocks
    private TipoMaterialServiceImpl tipoMaterialService;

    public TipoMaterialServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllReturnsEmptyList() {
        when(tipoMaterialRepository.findAll()).thenReturn(Collections.emptyList());
        List<TipoMaterialResponse> result = tipoMaterialService.findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByIdReturnsTipoMaterial() {
        TipoMaterial tipo = new TipoMaterial(1, "Orgánico");
        when(tipoMaterialRepository.findById(1)).thenReturn(Optional.of(tipo));
        Optional<TipoMaterialResponse> result = tipoMaterialService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("Orgánico", result.get().getNombreTipo());
    }

    @Test
    void testSaveTipoMaterial() {
        TipoMaterialRequest req = new TipoMaterialRequest();
        req.setNombreTipo("Plástico");
        TipoMaterial tipo = new TipoMaterial(2, "Plástico");
        when(tipoMaterialRepository.save(any(TipoMaterial.class))).thenReturn(tipo);
        TipoMaterialResponse resp = tipoMaterialService.save(req);
        assertNotNull(resp);
        assertEquals("Plástico", resp.getNombreTipo());
    }

    @Test
    void testUpdateTipoMaterial() {
        TipoMaterialRequest req = new TipoMaterialRequest();
        req.setNombreTipo("Metal");
        TipoMaterial tipo = new TipoMaterial(3, "Orgánico");
        when(tipoMaterialRepository.findById(3)).thenReturn(Optional.of(tipo));
        TipoMaterial tipoActualizado = new TipoMaterial(3, "Metal");
        when(tipoMaterialRepository.save(any(TipoMaterial.class))).thenReturn(tipoActualizado);
        TipoMaterialResponse resp = tipoMaterialService.update(3, req);
        assertNotNull(resp);
        assertEquals("Metal", resp.getNombreTipo());
    }

    @Test
    void testDeleteTipoMaterial() {
        doNothing().when(tipoMaterialRepository).deleteById(4);
        tipoMaterialService.delete(4);
        verify(tipoMaterialRepository, times(1)).deleteById(4);
    }
}
