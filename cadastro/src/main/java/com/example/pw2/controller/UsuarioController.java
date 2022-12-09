package com.example.pw2.controller;


import com.example.pw2.dto.LoginDTO;
import com.example.pw2.feign.Endpoint;
import com.example.pw2.model.Usuario;
import com.example.pw2.model.enumerations.Perfil;
import com.example.pw2.repository.UsuarioRepository;
import com.example.pw2.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/usuario")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioRepository repository;
    private final Endpoint endpoint;

    private final PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dto) {
        try {
            return ResponseEntity.status(200).body(service.getToken(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/salvar")
    @Transactional
    public ResponseEntity<?> salvar(@RequestBody Usuario usuario, @RequestHeader("token") String token) {
        try {
            if(endpoint.validarToken(token)) {
                usuario.setPerfil(Perfil.ROLE_ALUNO);
                usuario.setSenha(encoder.encode(usuario.getSenha()));
                return ResponseEntity.status(201).body(repository.save(usuario));
            } else {
                return ResponseEntity.status(401).body("Não Autorizado");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    @Transactional
    public ResponseEntity<?> editar(
            @RequestBody Usuario usuario,
            @PathVariable Long id,
            @RequestHeader("token") String token
    ) {
        try {
            if(endpoint.validarToken(token)) {
                usuario.setId(id);
                usuario.setSenha(encoder.encode(usuario.getSenha()));
                return ResponseEntity.status(200).body(repository.save(usuario));
            } else {
                return ResponseEntity.status(401).body("Não Autorizado");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id, @RequestHeader("token") String token) {
        try {
            if(endpoint.validarToken(token)) {
                repository.deleteById(id);
                return ResponseEntity.status(200).body("Deletado");
            } else {
                return ResponseEntity.status(401).body("Não Autorizado");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<?> pesquisar(@PathVariable Long id, @RequestHeader("token") String token) {
        try {
            if(endpoint.validarToken(token)) {
                Usuario usuario = repository.findById(id).orElseThrow();
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(401).body("Não Autorizado");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar(@RequestHeader("token") String token) {
        try {
            if(endpoint.validarToken(token)) {
                return ResponseEntity.ok().body(repository.findAll());
            } else {
                return ResponseEntity.status(401).body("Não Autorizado");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
