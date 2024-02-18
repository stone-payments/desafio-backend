package br.com.stone.lojastartwars.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.stone.lojastartwars.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction , Long>{
  
}
