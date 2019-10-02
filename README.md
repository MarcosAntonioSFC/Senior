<h1>Senior - Cidades.</h1>
  
## Description

* Avaliação do processo seletivo da Senior 10/2019
* Foi utilizado um banco de dados embedded (h2), portanto, para reiniciar os testes do zero, basta parar e subir a aplicação novamente.

## Frameworks
- [Java 8](https://www.java.com/pt_BR/download/faq/java8.xml) sendo a linguagem de programação.
- [Sprint Boot](https://spring.io/projects/spring-boot) como o framework principal.
- [Maven](https://maven.apache.org) para controle de dependencias e versões.

## Patterns
- [Restful](https://blog.caelum.com.br/rest-principios-e-boas-praticas/) sendo o padrão do endpoint
- [TDD](https://www.devmedia.com.br/test-driven-development-tdd-simples-e-pratico/18533) Em alguns pontos foram adicionados testes automatizados para garantir o bom funcionamento das funcionalidades críticas

## Database version control
- [Flyway](https://flywaydb.org)

## Installation
- Faça o download e a instalação da API do [Java](https://www.java.com/pt_BR/download/)
- Configure o Project Language Level para 8
- Instale o [Maven](http://www.matera.com/blog/post/tutorial-instalacao-apache-maven-configuracao-eclipse)

## Running the app
- 1º Abra o cmd (prompt de comando).
- 2º Escolha um diretório para baixar o projeto e clone utilizando o commando `git clone https://github.com/MarcosAntonioSFC/Senior.git` 
- 3º Para executar a aplicação utilize o comado `mvn clean package && java -jar target/cidade-1.0.jar`
- 4º Ou se preferir analisar o código enquanto for executado, localize o arquivo Application.java e clique em Run.
- 5º Para efetuar os testes dos endpoint's, basta utilizar algum client de http, foi utilizado o RClient durante o 
desenvolvimento. Para facilitar os testes basta importar o arquivo que estão no diretório `docs/senior.json` 
para seu RClient 

#### API's: Seguem o Padrão Resful
####Path: http://localhost:9000/senior/cidade/

#### 1 - Ler o arquivo CSV das cidades para a base de dados
**@POST** `http://localhost:9000/senior/cidade/upload/`

#### 2 - Retornar somente as cidades que são capitais ordenadas por nome;
**@GET** http://localhost:9000/senior/cidade/capitais/

#### 3 - Retornar o nome do estado com a maior e menor quantidade de cidades e a quantidade de cidades;
**@GET** `http://localhost:9000/senior/cidade/tamanho/estados/cidades/`

#### 4 - Retornar a quantidade de cidades por estado
**@GET** `http://localhost:9000/senior/cidade/cidades/estados/`

#### 5 - Obter os dados da cidade informando o id do IBGE; 
**@GET** `http://localhost:9000/senior/cidade/{id}`

#### 6 - Retornar o nome das cidades baseado em um estado selecionado;
**@GET** `http://localhost:9000/senior/cidade/by/estado/?uf="""`

#### 7 - Permitir adicionar uma nova Cidade; 
**@POST** `http://localhost:9000/senior/cidade/`
**Com a repesentação da cidade em Json. Exemplo: **
`{
   "id": "7777777",
   "estado": {
     "id": "SP",
     "sigla": "SP"
   },
   "nome": "Ribeirão Preto",
   "capital": false,
   "latitude": -11.9355403048,
   "longitude": -61.9998238963,
   "noAccents": "Ribeirao Preto",
   "nomeAlternativo": "",
   "microRegiao": "Ribeirao Preto",
   "mesoRegiao": "Ribeirao Preto"
 }
 `

#### 8 - Permitir deletar uma cidade; 
**@Delete** `http://localhost:9000/senior/cidade/{id}`

#### 9 - Permitir selecionar uma coluna (do CSV) e através dela entrar com uma string para filtrar. retornar assim todos os objetos que contenham tal string;
**@GET** `http://localhost:9000/senior/cidade/?column={nomedacoluna}&valor={valordaconsulta}`

#### 10 - Retornar a quantidade de registro baseado em uma coluna. Não deve contar itens iguais;
**@GET** `http://localhost:9000/senior/cidade/count/?column={nomedacoluna}` 

#### 11 - Retornar a quantidade de registros total; 
**@GET** `http://localhost:9000/senior/cidade/count/all/`

#### 12 - Dentre todas as cidades, obter as duas cidades mais distantes uma da outra com base na localização (distância em KM em linha reta);
**@GET** `http://localhost:9000/senior/cidade/mais/distantes/` 

## Padrão do retorno
####Pensando em facilitar uma possível integração com o frontend, foi definido um wrapper para o retorno dos request.
####Este wrapper contem a seguinte estrutura
`
{
    "data": null,
    "message": "",
    "status": null
}
`
####A saber:
######data -> dados da resposta
######message -> mensagem do servidor ao usuário
######status -> http code 