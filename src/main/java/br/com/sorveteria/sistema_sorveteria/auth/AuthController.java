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

        if (!"admin".equals(request.getUsername()) ||
                !"1234".equals(request.getPassword())) {
            return ResponseEntity.status(401).build();
        }

        String token = jwtService.gerarToken(request.getUsername());

        return ResponseEntity.ok(
                Map.of("token", token)
        );
    }
}
