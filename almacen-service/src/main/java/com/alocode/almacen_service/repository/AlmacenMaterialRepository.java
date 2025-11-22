package com.alocode.almacen_service.repository;

import com.alocode.almacen_service.entity.AlmacenMaterial;
import com.alocode.almacen_service.entity.AlmacenMaterialId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlmacenMaterialRepository extends JpaRepository<AlmacenMaterial, AlmacenMaterialId> {
}
