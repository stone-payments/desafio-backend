package br.com.stone.lojastartwars.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.stone.lojastartwars.entity.History;

public interface HistoryRepository extends CrudRepository<History, Long> {
}
