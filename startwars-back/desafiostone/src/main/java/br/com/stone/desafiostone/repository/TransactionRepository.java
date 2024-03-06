package br.com.stone.desafiostone.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.stone.desafiostone.models.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction , Long>{
  
}
