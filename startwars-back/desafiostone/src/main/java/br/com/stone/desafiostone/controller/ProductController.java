package br.com.stone.desafiostone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.stone.desafiostone.models.Product;
import br.com.stone.desafiostone.models.Response;
import br.com.stone.desafiostone.service.ProductService;

@RestController
@RequestMapping("/starstore/product")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    ProductService productService;

    @DeleteMapping("/remover/{codigo}")
    public ResponseEntity<Response> remover(@PathVariable long codigo) {
        return productService.remover(codigo);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Product product) {
        return productService.cadastrarAlterar(product, "cadastrar");
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@RequestBody Product product) {
        return productService.cadastrarAlterar(product, "alterar");
    }

    @GetMapping("/listar")
    public Iterable<Product> listar() {
        return productService.listar();
    }
}
