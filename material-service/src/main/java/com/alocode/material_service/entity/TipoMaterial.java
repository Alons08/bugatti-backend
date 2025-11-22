package com.alocode.material_service.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "tipo_material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoMaterial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tipo")
	private Integer idTipo;

	@Column(name = "nombre_tipo", nullable = false, length = 100)
	private String nombreTipo;
}
