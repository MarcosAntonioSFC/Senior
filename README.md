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
- Finalizar documentação a partir daqui