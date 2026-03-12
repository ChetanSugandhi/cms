package com.media.cms.service;

import com.media.cms.model.dto.UserRequest;
import com.media.cms.model.entity.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getById(Long id);
    User create(UserRequest request);
    User update(Long id, UserRequest request);
    void delete(Long id);
}
