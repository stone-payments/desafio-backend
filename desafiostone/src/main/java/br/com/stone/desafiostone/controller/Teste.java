package br.com.stone.desafiostone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Teste {

  @GetMapping("/")
  public String teste(){
    return "Hello word";
  }
  
}
