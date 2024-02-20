import { Component, OnInit } from '@angular/core';
import { Product } from '../models/Product';
import { ProductService } from '../service/Product.service';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent implements OnInit {

  // Objeto do tipo Product
  product: Product = new Product();

  // Variável para visibilidade dos botões
  btnCadastro = true;

  // Variável para visibilidade da tabela
  tabela = true;

  // Array de Products
  products: Product[] = [];

  // Construtor
  constructor(private productService: ProductService) {}

  // Método de inicialização
  ngOnInit(): void {
    this.selecionar();
  }

  // Método de seleção
  selecionar(): void {
    this.productService.selecionar()
      .subscribe(retorno => this.products = retorno);
  }

  // Método de cadastro
  cadastrar(): void {
    this.productService.cadastrar(this.product)
      .subscribe(retorno => { 
        // Cadastrar o Product no vetor
        this.products.push(retorno); 
        // Limpar formulário
        this.product = new Product();
        // Mensagem
        alert('Product cadastrado com sucesso!');
      });
  }

  // Método para selecionar um Product específico
  selecionarProduct(posicao: number): void {
    // Selecionar Product no vetor
    this.product = this.products[posicao];
    // Visibilidade dos botões
    this.btnCadastro = false;
    // Visibilidade da tabela
    this.tabela = false;
  }

  // Método para editar Products
  editar(): void {
    this.productService.editar(this.product)
      .subscribe(retorno => {
        // Obter posição do vetor onde está o Product
        const posicao = this.products.findIndex(obj => obj.id === retorno.id);
        // Alterar os dados do Product no vetor
        this.products[posicao] = retorno;
        // Limpar formulário
        this.product = new Product();
        // Visibilidade dos botões
        this.btnCadastro = true;
        // Visibilidade da tabela
        this.tabela = true;
        // Mensagem
        alert('Product alterado com sucesso!');
      });
  }

  // Método para remover Products
  remover(): void {
    this.productService.remover(this.product.id)
      .subscribe(() => {
        // Remover Product do vetor
        this.products = this.products.filter(prod => prod.id !== this.product.id);
        // Limpar formulário
        this.product = new Product();
        // Visibilidade dos botões
        this.btnCadastro = true;
        // Visibilidade da tabela
        this.tabela = true;
        // Mensagem
        alert('Product removido com sucesso!');
      });
  }

  // Método para cancelar
  cancelar(): void {
    // Limpar formulário
    this.product = new Product();
    // Visibilidade dos botões
    this.btnCadastro = true;
    // Visibilidade da tabela
    this.tabela = true;
  }
}
