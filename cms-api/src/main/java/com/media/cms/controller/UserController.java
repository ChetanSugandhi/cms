package com.media.cms.controller;

import com.media.cms.model.dto.UserRequest;
import com.media.cms.model.entity.User;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<List<User>> getAll();
    ResponseEntity<User> getById(Long id);
    ResponseEntity<User> create(UserRequest request);
    ResponseEntity<User> update(Long id, UserRequest request);
    ResponseEntity<Void> delete(Long id);
}
