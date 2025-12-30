package com.pm.auth.controller;

import com.pm.auth.dto.AuthResponse;
import com.pm.auth.dto.LoginRequest;
import com.pm.auth.dto.RegisterRequest;
import com.pm.auth.service.AuthServiceImpll;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthServiceImpll authService;

  public AuthController(AuthServiceImpll authServicee) {
    this.authService = authServicee;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
    authService.register(request);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }
}
