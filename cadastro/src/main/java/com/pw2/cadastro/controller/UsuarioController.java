package com.pw2.cadastro.controller;

import com.pw2.cadastro.model.Usuario;
import com.pw2.cadastro.model.enumerations.Perfil;
import com.pw2.cadastro.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/usuario")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class UsuarioController {

    private final UsuarioRepository repository;

    @PostMapping("/salvar")
    @Transactional
    public ResponseEntity<?> salvar(@RequestBody Usuario usuario) {
        try {
            usuario.setPerfil(Perfil.ROLE_ALUNO);
            return ResponseEntity.status(201).body(repository.save(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
