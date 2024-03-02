package br.com.stone.desafiostone.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.stone.desafiostone.models.History;
import br.com.stone.desafiostone.models.Response;
import br.com.stone.desafiostone.service.HistoryService;

@RestController
@RequestMapping("/starstore/history")
@CrossOrigin(origins = "*")
public class HistoryController {

    @Autowired
    HistoryService HistoryService;

    @DeleteMapping("/remover/{codigo}")
    public ResponseEntity<Response> remover(@PathVariable long codigo) {
        return HistoryService.remover(codigo);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody History history) {
        return HistoryService.cadastrarAlterar(history, "cadastrar");
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@RequestBody History history) {
        return HistoryService.cadastrarAlterar(history, "alterar");
    }

    @GetMapping("/listar")
    public Iterable<History> listar() {
        return HistoryService.listar();
    }

    @GetMapping("/listar/{clientId}")
    public ResponseEntity<Optional<History>> listarPorCliente(@PathVariable long clientId) {
        Optional<History> History = HistoryService.encontrarPorId(clientId);
        return ResponseEntity.ok(History);
    }
}
