package com.alocode.almacen_service;

import com.alocode.almacen_service.dto.request.AlmacenMaterialRequest;
import com.alocode.almacen_service.dto.response.AlmacenMaterialResponse;
import com.alocode.almacen_service.entity.AlmacenMaterial;
import com.alocode.almacen_service.entity.AlmacenMaterialId;
import com.alocode.almacen_service.repository.AlmacenMaterialRepository;
import com.alocode.almacen_service.service.impl.AlmacenMaterialServiceImpl;
import com.alocode.almacen_service.client.MaterialFeignClient;
import com.alocode.almacen_service.client.dto.MaterialClientResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlmacenMaterialServiceImplTest {
    @Mock
    private AlmacenMaterialRepository almacenMaterialRepository;
    @Mock
    private MaterialFeignClient materialFeignClient;
    @InjectMocks
    private AlmacenMaterialServiceImpl almacenMaterialService;

    public AlmacenMaterialServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrUpdate() {
        AlmacenMaterialRequest req = new AlmacenMaterialRequest();
        req.setIdAlmacen(1);
        req.setIdMaterial(2);
        req.setStockActual(100);
        req.setStockMinimo(10);
        AlmacenMaterialId id = new AlmacenMaterialId(1, 2);
        AlmacenMaterial almacenMaterial = AlmacenMaterial.builder().idAlmacen(1).idMaterial(2).stockActual(100).stockMinimo(10).build();
        when(almacenMaterialRepository.findById(id)).thenReturn(Optional.empty());
        when(almacenMaterialRepository.save(any(AlmacenMaterial.class))).thenReturn(almacenMaterial);
        MaterialClientResponse material = new MaterialClientResponse();
        material.setNombre("MaterialX");
        when(materialFeignClient.getMaterialById(2)).thenReturn(material);
        AlmacenMaterialResponse resp = almacenMaterialService.createOrUpdate(req);
        assertNotNull(resp);
        assertEquals(100, resp.getStockActual());
    }

    @Test
    void testGetByIdReturnsResponse() {
        AlmacenMaterialId id = new AlmacenMaterialId(1, 2);
        AlmacenMaterial almacenMaterial = AlmacenMaterial.builder().idAlmacen(1).idMaterial(2).stockActual(50).stockMinimo(5).build();
        when(almacenMaterialRepository.findById(id)).thenReturn(Optional.of(almacenMaterial));
        MaterialClientResponse material = new MaterialClientResponse();
        material.setNombre("MaterialY");
        when(materialFeignClient.getMaterialById(2)).thenReturn(material);
        AlmacenMaterialResponse resp = almacenMaterialService.getById(1, 2);
        assertNotNull(resp);
        assertEquals(50, resp.getStockActual());
    }

    @Test
    void testGetByAlmacenReturnsList() {
        AlmacenMaterial am1 = AlmacenMaterial.builder().idAlmacen(1).idMaterial(2).stockActual(20).stockMinimo(2).build();
        AlmacenMaterial am2 = AlmacenMaterial.builder().idAlmacen(1).idMaterial(3).stockActual(30).stockMinimo(3).build();
        AlmacenMaterial am3 = AlmacenMaterial.builder().idAlmacen(2).idMaterial(4).stockActual(40).stockMinimo(4).build();
        when(almacenMaterialRepository.findAll()).thenReturn(Arrays.asList(am1, am2, am3));
        MaterialClientResponse material = new MaterialClientResponse();
        material.setNombre("MaterialZ");
        when(materialFeignClient.getMaterialById(anyInt())).thenReturn(material);
        List<AlmacenMaterialResponse> resp = almacenMaterialService.getByAlmacen(1);
        assertEquals(2, resp.size());
    }

    @Test
    void testDelete() {
        AlmacenMaterialId id = new AlmacenMaterialId(1, 2);
        doNothing().when(almacenMaterialRepository).deleteById(id);
        almacenMaterialService.delete(1, 2);
        verify(almacenMaterialRepository, times(1)).deleteById(id);
    }
}
