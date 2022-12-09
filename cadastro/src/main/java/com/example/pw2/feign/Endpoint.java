package com.example.pw2.feign;

import com.example.pw2.dto.LoginDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Utiliza a dependência do openfeign para a comunicação com o serviço de auth
@FeignClient(value = "endpoint", url = "localhost:8081/auth/")
public interface Endpoint {

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    ResponseEntity<?> getTokenBearer(@RequestBody @Valid LoginDTO dto);

    @RequestMapping(method = RequestMethod.POST, value = "/validarToken")
    boolean validarToken(@RequestParam("token") String token);

}
