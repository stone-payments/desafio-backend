package br.com.stone.desafiostone.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.stone.desafiostone.entity.CreditCard;

public interface CreditCardRepository extends CrudRepository<CreditCard, Long>{
  
}
