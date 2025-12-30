package com.pm.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;

import com.pm.auth.dto.AuthResponse;
import com.pm.auth.dto.LoginRequest;
import com.pm.auth.dto.RegisterRequest;
import com.pm.security.jwt.JwtService;
import com.pm.user.entity.Users;
import com.pm.user.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepo,
                       PasswordEncoder encoder,
                       AuthenticationManager authManager,
                       JwtService jwtService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
        	throw new com.pm.common.exception.BadRequestException("Email already exists");
        }

        Users user = Users.builder()
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .build();

        userRepo.save(user);
    }

    public AuthResponse login(LoginRequest req) {
        Authentication auth = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
        authManager.authenticate(auth);

        String token = jwtService.generateToken(req.getEmail());
        return new AuthResponse(token);
    }
}
