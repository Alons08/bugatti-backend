package com.alocode.almacen_service.repository;

import com.alocode.almacen_service.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {
    
    @Query("SELECT COUNT(m) FROM Movimiento m WHERE DATE(m.fechaHora) = :fecha")
    Integer getMovimientosDelDia(@Param("fecha") LocalDate fecha);
    
    @Query("SELECT m FROM Movimiento m ORDER BY m.fechaHora DESC")
    List<Movimiento> findMovimientosRecientes();
}
