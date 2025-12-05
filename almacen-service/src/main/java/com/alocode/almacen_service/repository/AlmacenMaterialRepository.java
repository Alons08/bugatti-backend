package com.alocode.almacen_service.repository;

import com.alocode.almacen_service.entity.AlmacenMaterial;
import com.alocode.almacen_service.entity.AlmacenMaterialId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlmacenMaterialRepository extends JpaRepository<AlmacenMaterial, AlmacenMaterialId> {
    
    @Query("SELECT COALESCE(SUM(am.stockActual), 0) FROM AlmacenMaterial am")
    Integer getTotalStock();
    
    @Query("SELECT COUNT(am) FROM AlmacenMaterial am WHERE am.stockActual <= am.stockMinimo")
    Integer getMaterialesAgotados();
}
