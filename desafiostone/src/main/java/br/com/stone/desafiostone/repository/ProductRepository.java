package br.com.stone.desafiostone.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.stone.desafiostone.entity.Product;

public interface ProductRepository extends CrudRepository <Product, Long> {
  
}
