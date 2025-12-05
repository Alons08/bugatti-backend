package com.alocode.almacen_service.repository;

import com.alocode.almacen_service.entity.MovimientoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimientoDetalleRepository extends JpaRepository<MovimientoDetalle, Integer> {
    List<MovimientoDetalle> findByIdMovimiento(Integer idMovimiento);
}
