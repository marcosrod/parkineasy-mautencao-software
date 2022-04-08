package br.edu.utfpr.parkineasy.controller;

import br.edu.utfpr.parkineasy.dto.request.LoginRequest;
import br.edu.utfpr.parkineasy.dto.response.LoginResponse;
import br.edu.utfpr.parkineasy.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
