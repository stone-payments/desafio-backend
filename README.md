![StoneSDK](https://cloud.githubusercontent.com/assets/2567823/11539067/6300c838-990c-11e5-9831-4f8ce691859e.png)

# Desafio Backend

O desafio consiste em criar uma API REST para a loja de Star Wars que será consumida por um aplicativo (Android e iOS).
Todos os itens serão colocados em um carrinho do lado do aplicativo e passados para a API para realizar uma transação e-commerce.

> README original do desafio com a descrição dos requisitos em [./docs/README.md](./docs/README.md)

## Tecnologias:

- PHP
- Laravel
- PostgreSQL
- Redis

## Pre Requisitos:

- Docker Engine
- Docker Compose

## Como rodar:

1. entrar na pasta do projeto

```bash
cd star-store-laravel-app
```

2. subir containers

```bash
./vendor/bin/sail up -d
```

3. criar uma nova chave para a aplicação

```bash
./vendor/bin/sail artisan key:generate
```

4. instalar depedencias

```bash
./vendor/bin/sail composer install
```

5. criar as migrações para o banco de dados

```bash
./vendor/bin/sail artisan migrate
```

> se quiser popular seu banco de dados execute:
>
> `./vendor/bin/sail artisan migrate --seed`

7. fazer as requisições para `http://localhost:8989/api`

## Documentações:

### Api:

documentação das requisições estão em [docs/api/star-store-laravel-app.postman_collection](./docs/api/). Você consegue importa-las no Postman e no Insomnia

a documetação também esta hospedada no [postgres web](https://documenter.getpostman.com/view/22309579/2sA2xpSULe)

### Banco de dados:

na pasta [docs/db/diagrams](./docs/db/diagrams/) disponibilizei o diagrama ER do banco de dados.

# Melhorias a fazer:

- [ ] cache
- [ ] melhorar permissões da autenticação das rotas
