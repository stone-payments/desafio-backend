package br.com.stone.desafiostone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.stone.desafiostone.entity.CreditCard;
import br.com.stone.desafiostone.entity.Response;
import br.com.stone.desafiostone.entity.Transaction;
import br.com.stone.desafiostone.repository.CreditCardRepository;
import br.com.stone.desafiostone.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private Response responseMessage;

    public Iterable<Transaction> listar() {
        return transactionRepository.findAll();
    }

    public ResponseEntity<?> cadastrarAlterar(Transaction transaction, String acao) {
        if (transaction.getClient_name().isEmpty()) {
            responseMessage.setMensagem("O Nome do cliente é obrigatório");
            return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
        } else {
            if (transaction.getCredit_card() != null && transaction.getCredit_card().getId() == 0) {
                CreditCard creditCard = transaction.getCredit_card();
                CreditCard savedCreditCard = creditCardRepository.save(creditCard);
                transaction.setCredit_card(savedCreditCard);
            }
            if (acao.equals("cadastrar")) {
                return new ResponseEntity<Transaction>(transactionRepository.save(transaction), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Transaction>(transactionRepository.save(transaction), HttpStatus.OK);
            }
        }
    }

    public ResponseEntity<Response> remover(long codigo) {
        transactionRepository.deleteById(codigo);
        responseMessage.setMensagem("Transaçao removida com sucesso!!");
        return new ResponseEntity<Response>(responseMessage, HttpStatus.OK);
    }
}
