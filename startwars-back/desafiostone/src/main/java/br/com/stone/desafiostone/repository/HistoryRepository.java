package br.com.stone.desafiostone.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.stone.desafiostone.models.History;

public interface HistoryRepository extends CrudRepository<History, Long> {
}
