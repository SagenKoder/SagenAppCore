package app.sagen.core.service;

import app.sagen.core.model.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
