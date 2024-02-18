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

import br.com.stone.desafiostone.entity.Product;
import br.com.stone.desafiostone.entity.Response;
import br.com.stone.desafiostone.service.ProductService;

@RestController
@RequestMapping("/starstore/product")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    ProductService ps;

    @DeleteMapping("/remover/{codigo}")
    public ResponseEntity<Response> remover(@PathVariable long codigo) {
        return ps.remover(codigo);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Product pm) {
        return ps.cadastrarAlterar(pm, "cadastrar");
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@RequestBody Product pm) {
        return ps.cadastrarAlterar(pm, "alterar");
    }

    @GetMapping("/listar")
    public Iterable<Product> listar() {
        return ps.listar();
    }

    @GetMapping("/")
    public String rota() {
        return "API de produtos funcionando!";
    }
}
