package br.com.stone.lojastartwars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.stone.lojastartwars.entity.Response;
import br.com.stone.lojastartwars.entity.Transaction;
import br.com.stone.lojastartwars.repository.CreditCardRepository;
import br.com.stone.lojastartwars.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    public Iterable<Transaction> listar() {
        return transactionRepository.findAll();
    }

    public ResponseEntity<?> cadastrarAlterar(Transaction transaction, String acao) {
        if (transaction.getClient_name().isEmpty()) {
            return ResponseEntity.badRequest().body("O Nome do cliente é obrigatório");
        } else {
            if (transaction.getCredit_card() != null && transaction.getCredit_card().getId() == 0) {
                transaction.setCredit_card(creditCardRepository.save(transaction.getCredit_card()));
            }
            HttpStatus status = acao.equals("cadastrar") ? HttpStatus.CREATED : HttpStatus.OK;
            return new ResponseEntity<>(transactionRepository.save(transaction), status);
        }
    }

    public ResponseEntity<Response> remover(long codigo) {
        transactionRepository.deleteById(codigo);
        Response responseMessage = new Response();
        responseMessage.setMensagem("Transaçao removida com sucesso!!");
        return ResponseEntity.ok(responseMessage);
    }
}
