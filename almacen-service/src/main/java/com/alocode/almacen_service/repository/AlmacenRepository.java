package com.alocode.almacen_service.repository;

import com.alocode.almacen_service.entity.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlmacenRepository extends JpaRepository<Almacen, Integer> {
    List<Almacen> findByIdUsuario(Integer idUsuario);
}
