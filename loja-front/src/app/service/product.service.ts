import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/Product';

@Injectable({
  providedIn: 'root'
})

export class ProductService {

  // URL da API
  private url:string = 'http://localhost:8080/starstore/product';

  // Construtor
  constructor(private http: HttpClient) { }

  // Método para selecionar todos os Products
  selecionar(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.url}/listar`);
  }

  // Método para cadastrar Products
  cadastrar(product: Product): Observable<Product> {
    return this.http.post<Product>(`${this.url}/cadastrar`, product);
  }

  // Método para editar Products
  editar(product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.url}/alterar`, product);
  }

  // Método para remover Products
  remover(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/remover/${id}`);
  }
}
