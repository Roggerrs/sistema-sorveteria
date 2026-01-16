//package br.com.sorveteria.sistema_sorveteria.auth;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtService jwtService;
//
//    public AuthController(
//            AuthenticationManager authenticationManager,
//            JwtService jwtService
//    ) {
//        this.authenticationManager = authenticationManager;
//        this.jwtService = jwtService;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            request.getUsername(),
//                            request.getPassword()
//                    )
//            );
//        } catch (BadCredentialsException ex) {
//            return ResponseEntity
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .body(Map.of("error", "Usuário ou senha inválidos"));
//        }
//
//        String token = jwtService.gerarToken(request.getUsername());
//
//        return ResponseEntity.ok(Map.of("token", token));
//    }
//}
