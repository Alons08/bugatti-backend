package com.alocode.material_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_material")
	private Integer idMaterial;

	@ManyToOne
	@JoinColumn(name = "id_tipo", nullable = false)
	private TipoMaterial tipoMaterial;

	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado", nullable = false)
	private Boolean estado = true;

	@Column(name = "unidad_medida", length = 50)
	private String unidadMedida;
}
