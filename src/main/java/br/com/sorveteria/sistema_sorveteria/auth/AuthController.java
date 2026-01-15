package br.com.sorveteria.sistema_sorveteria.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // LOGIN SIMPLES (depois você melhora)
        if (!request.getUsername().equals("admin") ||
                !request.getPassword().equals("1234")) {
            return ResponseEntity.status(401).build();
        }

        String token = jwtService.gerarToken(request.getUsername());

        return ResponseEntity.ok(
                Map.of("token", token)
        );
    }
}