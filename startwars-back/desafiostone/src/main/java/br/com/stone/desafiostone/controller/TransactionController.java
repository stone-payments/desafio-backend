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

import br.com.stone.desafiostone.models.Response;
import br.com.stone.desafiostone.models.Transaction;
import br.com.stone.desafiostone.service.TransactionService;

@RestController
@RequestMapping("/starstore/buy")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @DeleteMapping("/remover/{codigo}")
    public ResponseEntity<Response> remover(@PathVariable long codigo) {
        return transactionService.remover(codigo);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Transaction transaction) {
        return transactionService.cadastrarAlterar(transaction, "cadastrar");
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@RequestBody Transaction transaction) {
        return transactionService.cadastrarAlterar(transaction, "alterar");
    }

    @GetMapping("/listar")
    public Iterable<Transaction> listar() {
        return transactionService.listar();
    }
}
