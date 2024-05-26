# Projeto EZPass

![Java](https://img.shields.io/badge/Linguagem-Java-orange?logo=java)
![IDE](https://img.shields.io/badge/IDE-IntelliJ%20%7C%20VSCode%20%7C%20Eclipse-blue?logo=visual-studio-code)
![SGBD](https://img.shields.io/badge/SGBD-PostgreSQL-blue?logo=postgresql)
![Bibliotecas](https://img.shields.io/badge/Bibliotecas-Lombok%20%7C%20Devtools%20%7C%20JPA-brightgreen)

## Primeiro Processo

### Instalação

#### Pré-requisitos:
- Java SDK 17
- IDE (IntelliJ, VSCode, Eclipse)
- PostgreSQL como SGBD

#### Passos:
1. Clone o projeto:
   ```
   git clone https://github.com/seu-usuario/ezpass.git
   ```

2. Instale as bibliotecas:
   ```
   ./mvnw install
   ```

3. Crie um banco de dados no PostgreSQL chamado `ezpassdb`.

4. No arquivo `src/main/resources/application.properties`, altere o `username` e `password` para o seu próprio do PostgreSQL.

5. Após fazer essas alterações, o projeto deve funcionar.

### Uso

Agora que o projeto está configurado, você pode iniciar o servidor e começar a utilizar o EZPass para suas necessidades de gerenciamento de passagens.
