package br.com.stone.desafiostone.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.stone.desafiostone.entity.History;

public interface HistoryRepository extends CrudRepository<History, Long> {
}
