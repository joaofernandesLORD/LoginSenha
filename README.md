<h1 align="center"> Login Senha </h1>

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

### Prefácio
Esse repositório tem como propósito demonstrar uma pequena parte de um projeto meu, como eu acabei demorando muito nesse processo e acredito que esse "pequena" funcionalidade já é algo muito complexo resolvi deixar ele aqui aberto ao público, Java já é complexo o suficiente para acabar com duas semanas minhas. 

## Resumo
Esse conjunto de código faz parte de um sistema de login e senha completo, para que você inicie o programa, comece a compilação pelo LoginSystem, ele é a main desse sistema. A partir dessa Tela você já consegue realizar o login, reset de senha e cadastro de novos usuários. Para que você possa realizar o login você deve ter seus dados no arquivo "credenciais.csv" onde ele tem uma organização de Usuário, senha, credencial; a ideia da credencial foi para desenvolver futuramente barrereis de informação para o sistema baseando no nível da sua credencial, com isso limitando as funcionalidades.

![Captura de tela 2025-04-16 134753](https://github.com/user-attachments/assets/f8667104-20b7-448b-ab9d-40e869888d6b)

O reset de senha faz uma busca através do arquivo "credenciais.csv" e modifica a sua senha, entretanto para gerar uma nova senha ela deve ser uma senha forte para o sistema, algo que ele avisa antes de restar, como ter 6 caracteres, ter letras maiúsculas, minusculas, números e caracteres especiais. Exemplo: Senha123@

A parte de cadastro realiza uma adição a lista existente no aquivo "credenciais.csv", com isso adicionando um novo usuário, senha e credencial, se alguma das informações forem repetidas (Menos o nível de credencial) o seu cadastro vai ser interrompido até que aquela informação seja trocada ou você cancele o processo de cadastro.

Uma coisa interessante é que a cada realização de atividades que modifiquem as informações, a ClassGerarTicket faz um ticket descrevendo as informações alteradas, para ser mais fácil a identificação de alterações no seu código.

### Administrador
Caso você queira fazer as modificações no sistema sem mexer no código (algo comum em um sistema de login e senha) você pode realizar o login como administrador, realizando o login como administrador você consegue ter acesso a uma janela especial, nela você tem algumas opções para modificar as informações do seu sistema, algumas dessas opções que você pode realizar é:

![Captura de tela 2025-04-16 111537](https://github.com/user-attachments/assets/1e447f23-0d96-4151-a730-6dd269337cec)

* `Cadastrar Usuário`: Nessa opção, você pode realizar o cadastro de novos usuários de forma conforme feito na tela de login;
* `Lista de Usuário`: Aqui você pode ter uma fácil visualização da sua lista de usuários e as informações atreladas;
* `Remover Usuário`: Nessa função, você pode remover o usuário que você preferir (não tem como desfazer, porém, pode ser adicionada uma função do somente desativar o funcionário e não apagar);
* `Modificar Usuário`: Você também pode modificar alguma coisa do usuário, como nome, senha e credencial;
* `Tela Principal`: Aqui você pode somente prosseguir para a tela do sistema (isso ocorre automaticamente quando você faz login sem ser ADM);
* `Sair`: Nessa última opção, você retorna para a tela de login e pode realizar o login em outro usuário se preferir.

### Tela do Sistema
Na tela do sistema teremos as funcionalidades do programa, entenda, esse código serve somente para ser um sistema de login e senha e nada mais que isso, por isso ele leva até a tela do sistema para que nesse momento você possa realizar as funções dele, como uma calculadora, um sistema de mapa, ou até mesmo ser um banco de dados personalizado, algum programa da sua opção.

Claro que, para deixar o sistema um loop para sua própria experiência, eu implementei um botão de logout onde você retorna até a tela de login onde pode entrar com outra conta.

#### Observações
O sistema contem um sistema onde, dependendo da credencial que você selecione para aquela conta quando você entre, faça com que ela apareça na borda da tela principal, além de que para realizar algumas funções, tem uma senha de segurança, essa senha é lulu2025.

# :hammer: Funcionalidades do sistema Login Senha

- `Funcionalidade 1`: Login e Logout;
- `Funcionalidade 2`: Manipulação de cadastro;
- `Funcionalidade 2a`: Apagar e adicionar cadastro;
- `Funcionalidade 3`: Ferramentas de autentificação.
