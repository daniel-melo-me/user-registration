package com.pw2.cadastro.openFeign;

import com.pw2.cadastro.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "token", url = "http:localhost:8081/token")
public interface Endpoint {

    @RequestMapping(method = RequestMethod.GET, value = "")
    public void getToken(Usuario usuario);

}
