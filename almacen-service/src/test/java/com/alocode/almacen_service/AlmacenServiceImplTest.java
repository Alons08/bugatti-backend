package com.alocode.almacen_service;

import com.alocode.almacen_service.dto.request.AlmacenRequest;
import com.alocode.almacen_service.dto.response.AlmacenResponse;
import com.alocode.almacen_service.entity.Almacen;
import com.alocode.almacen_service.repository.AlmacenRepository;
import com.alocode.almacen_service.service.impl.AlmacenServiceImpl;
import com.alocode.almacen_service.client.UsuarioFeignClient;
import com.alocode.almacen_service.client.dto.UsuarioClientResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlmacenServiceImplTest {
    @Mock
    private AlmacenRepository almacenRepository;
    @Mock
    private UsuarioFeignClient usuarioFeignClient;
    @InjectMocks
    private AlmacenServiceImpl almacenService;

    public AlmacenServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAlmacenByIdReturnsResponse() {
        Almacen almacen = Almacen.builder()
            .idAlmacen(1)
            .idUsuario(2)
            .codigoAlmacen("A001")
            .nombre("Central")
            .build();
        when(almacenRepository.findById(1)).thenReturn(Optional.of(almacen));
        UsuarioClientResponse usuario = new UsuarioClientResponse();
        usuario.setNombre("Juan Perez");
        when(usuarioFeignClient.getUsuarioById(2)).thenReturn(usuario);
        AlmacenResponse resp = almacenService.getAlmacenById(1);
        assertNotNull(resp);
        assertEquals("Central", resp.getNombre());
        assertEquals("Juan Perez", resp.getNombreUsuario());
    }

    @Test
    void testDeleteAlmacen() {
        doNothing().when(almacenRepository).deleteById(1);
        almacenService.deleteAlmacen(1);
        verify(almacenRepository, times(1)).deleteById(1);
    }

    @Test
    void testCreateAlmacen() {
        AlmacenRequest req = new AlmacenRequest();
        req.setIdUsuario(2);
        req.setCodigoAlmacen("A002");
        req.setNombre("Secundario");
        Almacen almacen = Almacen.builder()
            .idAlmacen(10)
            .idUsuario(2)
            .codigoAlmacen("A002")
            .nombre("Secundario")
            .build();
        when(almacenRepository.save(any(Almacen.class))).thenReturn(almacen);
        UsuarioClientResponse usuario = new UsuarioClientResponse();
        usuario.setNombre("Maria Lopez");
        when(usuarioFeignClient.getUsuarioById(2)).thenReturn(usuario);
        AlmacenResponse resp = almacenService.createAlmacen(req);
        assertNotNull(resp);
        assertEquals("Secundario", resp.getNombre());
        assertEquals("Maria Lopez", resp.getNombreUsuario());
    }

    @Test
    void testUpdateAlmacen() {
        AlmacenRequest req = new AlmacenRequest();
        req.setCodigoAlmacen("A003");
        req.setNombre("Actualizado");
        Almacen almacen = Almacen.builder()
            .idAlmacen(5)
            .idUsuario(2)
            .codigoAlmacen("A001")
            .nombre("Viejo")
            .build();
        when(almacenRepository.findById(5)).thenReturn(Optional.of(almacen));
        Almacen almacenActualizado = Almacen.builder()
            .idAlmacen(5)
            .idUsuario(2)
            .codigoAlmacen("A003")
            .nombre("Actualizado")
            .build();
        when(almacenRepository.save(any(Almacen.class))).thenReturn(almacenActualizado);
        UsuarioClientResponse usuario = new UsuarioClientResponse();
        usuario.setNombre("Carlos Ruiz");
        when(usuarioFeignClient.getUsuarioById(2)).thenReturn(usuario);
        AlmacenResponse resp = almacenService.updateAlmacen(5, req);
        assertNotNull(resp);
        assertEquals("Actualizado", resp.getNombre());
        assertEquals("Carlos Ruiz", resp.getNombreUsuario());
    }

    @Test
    void testGetAllAlmacenes() {
        Almacen almacen1 = Almacen.builder().idAlmacen(1).idUsuario(2).codigoAlmacen("A001").nombre("Central").build();
        Almacen almacen2 = Almacen.builder().idAlmacen(2).idUsuario(3).codigoAlmacen("A002").nombre("Secundario").build();
        when(almacenRepository.findAll()).thenReturn(java.util.Arrays.asList(almacen1, almacen2));
        UsuarioClientResponse usuario1 = new UsuarioClientResponse();
        usuario1.setNombre("Juan Perez");
        UsuarioClientResponse usuario2 = new UsuarioClientResponse();
        usuario2.setNombre("Maria Lopez");
        when(usuarioFeignClient.getUsuarioById(2)).thenReturn(usuario1);
        when(usuarioFeignClient.getUsuarioById(3)).thenReturn(usuario2);
        java.util.List<AlmacenResponse> resp = almacenService.getAllAlmacenes();
        assertEquals(2, resp.size());
        assertEquals("Central", resp.get(0).getNombre());
        assertEquals("Juan Perez", resp.get(0).getNombreUsuario());
        assertEquals("Secundario", resp.get(1).getNombre());
        assertEquals("Maria Lopez", resp.get(1).getNombreUsuario());
    }
}
