package com.example.pw2.service;

import com.example.pw2.dto.LoginDTO;
import com.example.pw2.feign.Endpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final Endpoint endpoint;

    public ResponseEntity<?> getToken(LoginDTO dto) {
        return endpoint.getTokenBearer(dto);
    }

}
