package com.media.cms.controller.impl;

import com.media.cms.model.dto.AuthRequest;
import com.media.cms.model.dto.AuthResponse;
import com.media.cms.model.dto.RegisterRequest;
import com.media.cms.model.entity.User;
import com.media.cms.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl implements com.media.cms.controller.AuthController {

    private final AuthService authService;

    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/getUserId")
    public long getCurrentLoggedInUserId() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      User userDetailsService1 = (User) authentication.getPrincipal();

      return userDetailsService1.getId();
  }
}
