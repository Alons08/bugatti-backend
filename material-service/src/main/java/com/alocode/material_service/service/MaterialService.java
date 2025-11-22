
package com.alocode.material_service.service;
import com.alocode.material_service.dto.request.MaterialRequest;
import com.alocode.material_service.dto.response.MaterialResponse;
import java.util.List;
import java.util.Optional;

public interface MaterialService {
    List<MaterialResponse> findAll();
    Optional<MaterialResponse> findById(Integer id);
    MaterialResponse save(MaterialRequest request);
    MaterialResponse update(Integer id, MaterialRequest request);
    void delete(Integer id);
}
