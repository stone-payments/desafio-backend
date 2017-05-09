package glx.com.StarWarsStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import glx.com.StarWarsStore.entities.Product;

public interface  ProductRepository extends JpaRepository<Product, Long>{




}
