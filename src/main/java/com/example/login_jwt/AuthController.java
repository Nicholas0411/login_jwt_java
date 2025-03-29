package com.example.login_jwt;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*") 
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    private final Map<String, String> USUARIOS = Map.of(
        "admin", "1234",
        "usuario", "abcd"

    );

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Map<String, String> datos){

        String user = datos.get("usuario");
        String pass = datos.get("password");

        if(USUARIOS.containsKey(user) && USUARIOS.get(user).equals(pass)){
            String token = jwtUtil.generateToken(user);
            return Map.of("token", token);
        } else {
            throw new RuntimeException("Credenciales Invalidas");
        }
    }

    @GetMapping("/protegido")
    public Map<String, String> protegido() {
        return Map.of("Mensaje", "Accediste a una ruta protegida");
    }
    
}
