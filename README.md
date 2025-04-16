<h1 align="center"> Login Senha </h1>

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

### Prefácio
Esse repositorio tem como próprosito demonstrar uma pequena parte de um projeto meu, como eu acabei demorando muito nesse processo e acredito que esse "pequena" funcionalidade já é algo muito complexo resolvi deixar ele aqui aberto ao publico, Java já é copmplexo o suficiente para acabar com duas semanas minhas. 

## Resumo
Esse conjunto de código faz parte de um sistem de Login e senha completo, para que você inicie o programa comece a compilação pelo LoginSystem, ele é a main desse sistema, apartir dessa Tela você já consegue realizar o login, reset de senha e cadastro de novos usuarios. Para que você possa realizar o login você deve ter seus dados no arquivo "credenciais.csv" onde ele tem uma organização de Usuario, senha, credencial; a ideia da credencial foi para desenvolver futuramente barrereis de informção para o sistema baseando no nivel da sua credencial, com isso limitando as funcionalidades.

O reset de senha faz uma busca através do arquivo "credenciais.csv" e modifica a sua senha, entretanto para gerar uma nova senha ela deve ser uma senha forte para o sistema, algo que ele avisa antes de resetar, como ter 6 caracteres, ter letras maiusculas, minusculas, numeros e caractere especial. Exemplo: Senha123@

A parte de cadastro realiza uma adição a lista existente no aquivo "credenciais.csv", com isso adicionando um novo usuario, senha e credencial, se alguma das informações forem repetidas (com exessão do nivel de credencial) o seu cadastro vai ser interrompido até que aquela informação seja trocada ou você cancele o processo de cadastro.

Uma coisa interessante é que a cada realização de atividades que modifiquem as informações a ClassGerarTicket faz um ticket descrevendo as informações que foram alteradas, para que seja mais facil a identificação de alterações no seu código.

### Administrador
Caso você queira fazer as modificações no sistema sem mexer no código (algo comomum em um sistema de login e senha) você pode realizar o login como administrador, realizando o logi como admiistrador você consegue ter acesso a uma janela especial, nela você tem algumas opções para modificar as informações do seu sistema, algumas dessas opções que você pode realizar é:

* `Cadastrar Usuário`: Nessa opção você pode realizar o cadastro de novos usuários de forma como é feito na tela de login;
* `Lista de Usuário`: Aqui você pode ter uma facil visualização da sua lista de usuários e as informações atreladaas;
* `Remover Usuário`: Nessa função você pode remover o usuário que você preferir (não tem como desfazer, pórem pode ser adicionado uma função do somente desativar o funcionario enão apagar);
* `Modificar Usuário`: Você também pode modificar alguma coisa do usuário, como nome, senha e credencial;
* `Tela Principal`: Aqui você pode somente prosseguir para a tela do sistema (isso ocorre automaticamente quando você faz login sem ser ADM);
* `Sair`: Nessa ultima opção você retorna para a tela de login e pode realizar o login em outro usuário se preferir.

# :hammer: Funcionalidades do sistema Login Senha

- `Funcionalidade 1`: descrição da funcionalidade 1
- `Funcionalidade 2`: descrição da funcionalidade 2
- `Funcionalidade 2a`: descrição da funcionalidade 2a relacionada à funcionalidade 2
- `Funcionalidade 3`: descrição da funcionalidade 3
