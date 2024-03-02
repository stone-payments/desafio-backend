package br.com.stone.desafiostone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.stone.desafiostone.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
