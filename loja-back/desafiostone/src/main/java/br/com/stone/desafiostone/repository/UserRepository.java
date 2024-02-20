package br.com.stone.desafiostone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.stone.desafiostone.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
