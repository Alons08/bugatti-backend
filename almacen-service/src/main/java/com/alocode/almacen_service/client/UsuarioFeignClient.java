package com.alocode.almacen_service.client;

import com.alocode.almacen_service.client.dto.UsuarioClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service")
public interface UsuarioFeignClient {
    @GetMapping("/admin/user/{id}")
    UsuarioClientResponse getUsuarioById(@PathVariable("id") Integer id);
}
