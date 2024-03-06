package br.com.stone.desafiostone.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.stone.desafiostone.models.Product;

public interface ProductRepository extends CrudRepository <Product, Long> {
  
}
