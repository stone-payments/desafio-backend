package br.com.stone.desafiostone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.stone.desafiostone.entity.Product;
import br.com.stone.desafiostone.entity.Response;
import br.com.stone.desafiostone.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository pr;

    @Autowired
    private Response rm;

    public Iterable<Product> listar() {
        return pr.findAll();
    }

    public ResponseEntity<?> cadastrarAlterar(Product pm, String acao) {
        if (pm.getTitle().equals("")) {
            rm.setMensagem("O título do produto é obrigatório");
            return new ResponseEntity<Response>(rm, HttpStatus.BAD_REQUEST);
        } else if (pm.getSeller().equals("")) {
            rm.setMensagem("O vendedor do produto é obrigatório");
            return new ResponseEntity<Response>(rm, HttpStatus.BAD_REQUEST);
        } else {
            if (acao.equals("cadastrar")) {
                return new ResponseEntity<Product>(pr.save(pm), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Product>(pr.save(pm), HttpStatus.OK);
            }
        }
    }

    public ResponseEntity<Response> remover(long codigo) {
        pr.deleteById(codigo);
        rm.setMensagem("Produto removido com sucesso!!");
        return new ResponseEntity<Response>(rm, HttpStatus.OK);
    }
}
