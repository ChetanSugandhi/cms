package com.media.cms.controller.impl;

import com.media.cms.model.dto.AuthRequest;
import com.media.cms.model.dto.AuthResponse;
import com.media.cms.model.dto.RegisterRequest;
import com.media.cms.model.entity.User;
import com.media.cms.repository.UserRepository;
import com.media.cms.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl implements com.media.cms.controller.AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthControllerImpl(AuthService authService,
                              UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
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
    public Map<String, Long> getCurrentLoggedInUserId() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String email = authentication.getName();

      User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found with email : " + email));


      return Map.of("id", user.getId());
  }
}
