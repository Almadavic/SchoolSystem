# SchoolSystem
--------SOBRE O QUE É A APLICAÇÃO--------

Foi desenvolvida uma aplicação BACK-END que simula um sistema escolar básico, como o ensino
fundamental.

--------OBJETIVO-------- 

Colocar em prática muitos dos conhecimentos que eu adquiri durante um ano e meio de estudo.

--------FUNCIONALIDADES-------- 

Um professor se autentica / Um aluno se autentica / Um diretor se autentica.

Um diretor visualiza todas as salas cadastradas no sistema.

Um diretor visualiza uma classe em específico / O professor visualiza uma classe associada a ele.

Um diretor visualiza a lista de alunos de uma sala específica / O professor visualiza a lista de alunos de uma classe associada a ele.

Um diretor visualiza um aluno específico de uma classe / O professor visualiza um aluno específico de uma classe associado a ele.

Um diretor visualiza um professor de uma classe /  O professor visualiza o professor de uma classe se ele for o professor dessa classe.

Um professor altera as notas de um aluno / Automaticamente após o UPGRADE das notas, se a média das notas for acima de 6.0 -> O sistema aprova o aluno.

Um diretor cria uma sala no sistema.

Um diretor coloca um professor em uma determinada classe.

Um diretor adiciona um aluno em uma classe.

Um diretor remove um aluno de uma classe.

Um diretor remove um professor de uma classe.

Um diretor remove uma classe.

Um professor consegue ver seus dados / Um aluno consegue ver seus dados / Um diretor consegue ver seus dados.

Um professor consegue trocar sua senha / Um aluno consegue trocar sua senha / Um diretor consegue trocar sua senha.

Um diretor visualiza todos os alunos cadastrados no sistema. 

Um diretor visualiza todos os alunos cadastrados no sistema que não tem nenhuma sala de aula associada.

Um diretor visualiza um aluno em específico.

Um diretor cadastra um aluno no sistema.

Um diretor visualiza todos os professores cadastrados no sistema. 

Um diretor visualiza todos os professores cadastrados no sistema que não tem nenhuma sala de aula associada.

Um diretor visualiza um professor em específico.

Um diretor cadastra um professor no sistema.

Um diretor encontra TODOS os USUÁRIOS do sistema.

Um diretor encontra TODOS os USUÁRIOS do sistema baseados em uma ROLE específica.

Um diretor encontra um usuário em específico do sistema.

Um diretor remove um usuário do sistema.

---------VERSIONAMENTO-------- 

Versão do Java : 17

Versão do Spring Boot : 2.6.7

--------FERRAMENTAS---------

Linguagem de Desenvolvimento :

       Java

Principal Framework : 

      Spring Boot.

Dependências : 

     Cache -> Utilizado para melhorar o desempenho da aplicação, evitando buscas desnecessárias no banco, deixando os dados em cache.
     
     Data JPA - > Utilizando o JPA para fazer a ponte entre o banco de dados e a aplicação -> ORM
     
     Validation -> Utilizado para proibir requisições com dados inválidos ou com um formato inválido.
     
     Web -> É uma aplicação WEB, Utilizado para receber requisições, devolver uma resposta ...
     
     DevTools -> Utilizado para não ter que reiniciar a aplicação manualmente sempre que uma alteração for feita.
     
     H2 - > Utilizado esse banco em MEMÓRIA para fazer testes, ambiente de teste.
     
     PostgreSQL -> Utilizado esse BANCO no ambiente de desenvolvimento e produção.
     
     Test -> Foram feitos testes na aplicação com JUNIT ( Testes de integração e Unidade).
     
     JsonWebToken -> Foi utilizado a arquitetura REST, por isso fiz a a aplicação ser stateless.
     
     Security -> Utilizado para fazer a segurança do sistema - > Autorização e Autenticação.
     
     SpringDoc -> Utilizado para documentar a API através dos ENDPOINTS.
     
     LomBok -> Utilizado para evitar muitas linhas de código.

Programas Utilizados : 

    Heroku - Utilizado para deixar o projeto na nuvem.
    
    Postman - Utilizado para fazer as requisições (CONSUMIR) a aplicação.
    
    IntelliJ - IDE escolhida para desenvolver o projeto.
    
    pgAdmin4 - Plataforma utilizada para fazer a manipulação e a leitura dos dados de uma base de dados do banco PostgreSQL.
    
    Git e GitHub - Utilizados para commitar o projeto e subir o código para a nuvem(remoto).

Bancos de Dados : 

    PostgreSQL - Usado em ambiente de desenvolvimento e produção.
    
    H2 - Usado em ambiente de teste.

------------------------- INFORMAÇÕES ADICIONAIS ---------------------

    Linhas de Código : 3170
    
    Classes / Arquivos implementados : 124

------------------------ LINKS -----------------------------

   Explicação da API em vídeo no YouTube : https://www.youtube.com/watch?v=RbJkriLaTKs 
   
   Perfil no Linkedin : https://www.linkedin.com/in/victoralmada/
