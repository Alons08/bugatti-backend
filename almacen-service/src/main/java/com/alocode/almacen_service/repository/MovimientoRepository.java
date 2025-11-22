package com.alocode.almacen_service.repository;

import com.alocode.almacen_service.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {
}
