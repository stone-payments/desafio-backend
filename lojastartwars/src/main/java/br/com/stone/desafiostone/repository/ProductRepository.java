package br.com.stone.lojastartwars.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.stone.lojastartwars.entity.Product;

public interface ProductRepository extends CrudRepository <Product, Long> {
  
}
