package app.sagen.core.service;

public interface SecurityService {
    String findLoggedInUsername();
    void autoLogin(String username, String password);
}