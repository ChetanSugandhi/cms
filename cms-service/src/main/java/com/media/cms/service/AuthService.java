package com.media.cms.service;

import com.media.cms.model.dto.AuthRequest;
import com.media.cms.model.dto.AuthResponse;
import com.media.cms.model.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
}
