package br.com.stone.desafiostone.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.stone.desafiostone.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

}

