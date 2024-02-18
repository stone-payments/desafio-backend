package br.com.stone.lojastartwars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.stone.lojastartwars.entity.History;
import br.com.stone.lojastartwars.entity.Response;
import br.com.stone.lojastartwars.repository.HistoryRepository;

import java.util.Optional;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository HistoryRepository;

    public Iterable<History> listar() {
        return HistoryRepository.findAll();
    }

    public ResponseEntity<History> cadastrarAlterar(History History, String acao) {
        HttpStatus status = acao.equals("cadastrar") ? HttpStatus.CREATED : HttpStatus.OK;
        return new ResponseEntity<>(HistoryRepository.save(History), status);
    }

    public ResponseEntity<Response> remover(long codigo) {
        HistoryRepository.deleteById(codigo);
        Response responseMessage = new Response();
        responseMessage.setMensagem("Historico removido com sucesso!!");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public Optional<History> encontrarPorId(long id) {
        return HistoryRepository.findById(id);
    }
}
