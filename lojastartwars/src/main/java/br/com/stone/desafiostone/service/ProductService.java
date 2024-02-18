package br.com.stone.lojastartwars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.stone.lojastartwars.entity.Product;
import br.com.stone.lojastartwars.entity.Response;
import br.com.stone.lojastartwars.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Response responseMessage;

    public Iterable<Product> listar() {
        return productRepository.findAll();
    }

    public ResponseEntity<?> cadastrarAlterar(Product product, String acao) {
        if (product.getTitle().isEmpty()) {
            responseMessage.setMensagem("O título do produto é obrigatório");
            return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
        } else if (product.getSeller().isEmpty()) {
            responseMessage.setMensagem("O vendedor do produto é obrigatório");
            return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
        } else {
            if (acao.equals("cadastrar")) {
                return new ResponseEntity<Product>(productRepository.save(product), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Product>(productRepository.save(product), HttpStatus.OK);
            }
        }
    }

    public ResponseEntity<Response> remover(long codigo) {
        productRepository.deleteById(codigo);
        responseMessage.setMensagem("Produto removido com sucesso!!");
        return new ResponseEntity<Response>(responseMessage, HttpStatus.OK);
    }
}
