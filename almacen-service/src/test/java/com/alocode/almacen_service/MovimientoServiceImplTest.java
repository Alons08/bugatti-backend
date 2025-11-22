package com.alocode.almacen_service;

import com.alocode.almacen_service.dto.request.MovimientoRequest;
import com.alocode.almacen_service.dto.request.MovimientoDetalleRequest;
import com.alocode.almacen_service.dto.response.MovimientoResponse;
import com.alocode.almacen_service.entity.Movimiento;
import com.alocode.almacen_service.entity.MovimientoDetalle;
import com.alocode.almacen_service.entity.TipoMovimiento;
import com.alocode.almacen_service.repository.MovimientoRepository;
import com.alocode.almacen_service.repository.MovimientoDetalleRepository;
import com.alocode.almacen_service.service.impl.MovimientoServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimientoServiceImplTest {
    @Mock
    private MovimientoRepository movimientoRepository;
    @Mock
    private MovimientoDetalleRepository movimientoDetalleRepository;
    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    public MovimientoServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMovimiento() {
        MovimientoDetalleRequest detReq = new MovimientoDetalleRequest();
        detReq.setIdMaterial(2);
        detReq.setCantidad(10);
        MovimientoRequest req = new MovimientoRequest();
        req.setIdAlmacen(1);
        req.setIdUsuario(3);
        req.setTipoMovimiento("entrada");
        req.setDescripcion("desc");
        req.setReferencia("ref");
        req.setDetalles(Arrays.asList(detReq));
        Movimiento mov = Movimiento.builder().idMovimiento(5).idAlmacen(1).idUsuario(3).tipoMovimiento(TipoMovimiento.entrada).descripcion("desc").referencia("ref").build();
        when(movimientoRepository.save(any(Movimiento.class))).thenReturn(mov);
        MovimientoDetalle detalle = MovimientoDetalle.builder().idDetalle(7).idMovimiento(5).idMaterial(2).cantidad(10).build();
        when(movimientoDetalleRepository.save(any(MovimientoDetalle.class))).thenReturn(detalle);
        MovimientoResponse resp = movimientoService.createMovimiento(req);
        assertNotNull(resp);
        assertEquals(5, resp.getIdMovimiento());
        assertEquals("entrada", resp.getTipoMovimiento());
        assertEquals(1, resp.getDetalles().size());
        assertEquals(10, resp.getDetalles().get(0).getCantidad());
    }

    @Test
    void testGetMovimientoByIdReturnsResponse() {
        Movimiento mov = Movimiento.builder().idMovimiento(5).idAlmacen(1).idUsuario(3).tipoMovimiento(TipoMovimiento.salida).descripcion("desc").referencia("ref").build();
        when(movimientoRepository.findById(5)).thenReturn(Optional.of(mov));
        MovimientoDetalle detalle = MovimientoDetalle.builder().idDetalle(8).idMovimiento(5).idMaterial(2).cantidad(20).build();
        when(movimientoDetalleRepository.findAll()).thenReturn(Arrays.asList(detalle));
        MovimientoResponse resp = movimientoService.getMovimientoById(5);
        assertNotNull(resp);
        assertEquals(5, resp.getIdMovimiento());
        assertEquals("salida", resp.getTipoMovimiento());
        assertEquals(1, resp.getDetalles().size());
        assertEquals(20, resp.getDetalles().get(0).getCantidad());
    }

    @Test
    void testGetMovimientosByAlmacenReturnsList() {
        Movimiento mov1 = Movimiento.builder().idMovimiento(1).idAlmacen(1).idUsuario(3).tipoMovimiento(TipoMovimiento.entrada).build();
        Movimiento mov2 = Movimiento.builder().idMovimiento(2).idAlmacen(1).idUsuario(4).tipoMovimiento(TipoMovimiento.salida).build();
        Movimiento mov3 = Movimiento.builder().idMovimiento(3).idAlmacen(2).idUsuario(5).tipoMovimiento(TipoMovimiento.entrada).build();
        when(movimientoRepository.findAll()).thenReturn(Arrays.asList(mov1, mov2, mov3));
        MovimientoDetalle detalle1 = MovimientoDetalle.builder().idDetalle(1).idMovimiento(1).idMaterial(2).cantidad(10).build();
        MovimientoDetalle detalle2 = MovimientoDetalle.builder().idDetalle(2).idMovimiento(2).idMaterial(3).cantidad(20).build();
        when(movimientoDetalleRepository.findAll()).thenReturn(Arrays.asList(detalle1, detalle2));
        List<MovimientoResponse> resp = movimientoService.getMovimientosByAlmacen(1);
        assertEquals(2, resp.size());
    }

    @Test
    void testDeleteMovimiento() {
        doNothing().when(movimientoRepository).deleteById(5);
        movimientoService.deleteMovimiento(5);
        verify(movimientoRepository, times(1)).deleteById(5);
    }
}
