package com.alocode.almacen_service.client;

import com.alocode.almacen_service.client.dto.MaterialClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "material-service")
public interface MaterialFeignClient {
    @GetMapping("/materiales/{id}")
    MaterialClientResponse getMaterialById(@PathVariable("id") Integer id);
}
