package com.pm.auth.service;

import com.pm.auth.dto.AuthResponse;
import com.pm.auth.dto.LoginRequest;
import com.pm.auth.dto.RegisterRequest;

//  interface first design principle
public interface AuthService {

  void register(RegisterRequest req);

  AuthResponse login(LoginRequest req);
}
