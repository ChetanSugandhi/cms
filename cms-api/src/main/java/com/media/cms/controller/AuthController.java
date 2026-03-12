package com.media.cms.controller;

import com.media.cms.model.dto.AuthRequest;
import com.media.cms.model.dto.AuthResponse;
import com.media.cms.model.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthController {
    ResponseEntity<AuthResponse> register(RegisterRequest request);
    ResponseEntity<AuthResponse> login(AuthRequest request);
}
