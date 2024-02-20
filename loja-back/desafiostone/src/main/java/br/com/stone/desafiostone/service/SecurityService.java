package br.com.stone.desafiostone.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
