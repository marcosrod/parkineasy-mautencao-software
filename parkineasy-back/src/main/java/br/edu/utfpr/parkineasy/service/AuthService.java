package br.edu.utfpr.parkineasy.service;

import br.edu.utfpr.parkineasy.dto.request.LoginRequest;
import br.edu.utfpr.parkineasy.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
