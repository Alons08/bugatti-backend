package com.alocode.almacen_service.repository;

import com.alocode.almacen_service.entity.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlmacenRepository extends JpaRepository<Almacen, Integer> {
}
