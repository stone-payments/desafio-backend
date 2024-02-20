package br.com.stone.desafiostone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.stone.desafiostone.models.Response;
import br.com.stone.desafiostone.models.User;
import br.com.stone.desafiostone.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Response response;

    public Iterable<User> listar() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> cadastrarAlterar(User user, String acao) {
        if (user.getUsername().isEmpty()) {
            response.setMensagem("O Nome do cliente é obrigatório");
        } else if (user.getPassword().isEmpty()) {
            response.setMensagem("a senha e  obrigatirio ");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Response> remover(long codigo) {
        userRepository.deleteById(codigo);
        response.setMensagem("Transaçao removida com sucesso!!");
        return ResponseEntity.ok(response);
    }
}
