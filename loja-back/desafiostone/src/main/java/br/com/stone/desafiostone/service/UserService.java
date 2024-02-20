package br.com.stone.desafiostone.service;



import java.util.List;

import br.com.stone.desafiostone.dto.UserDto;
import br.com.stone.desafiostone.models.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
