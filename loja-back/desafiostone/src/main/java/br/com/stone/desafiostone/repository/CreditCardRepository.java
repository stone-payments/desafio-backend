package br.com.stone.desafiostone.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.stone.desafiostone.models.CreditCard;

public interface CreditCardRepository extends CrudRepository<CreditCard, Long>{
  
}
