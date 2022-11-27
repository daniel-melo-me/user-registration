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

    @PutMapping("/editar/{id}")
    @Transactional
    public ResponseEntity<?> editar(
            @RequestBody Usuario usuario,
            @PathVariable Long id
    ) {
        try {
            usuario.setId(id);
            return ResponseEntity.status(200).body(repository.save(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.status(200).body("Deletado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<?> pesquisar(@PathVariable Long id) {
        try {
            Usuario usuario = repository.findById(id).orElseThrow();
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok().body(repository.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
