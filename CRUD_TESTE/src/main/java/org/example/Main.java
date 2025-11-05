package org.example;

// Imports do Banco de Dados

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Imports do DAO (Data Access Object)
import static org.example.UsuarioDao.*;


public class Main {

    // --- 1. Configurações do Banco de Dados ---
    private static final String URL_DB = "jdbc:mysql://localhost:3306/crud_java";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // Senha já está correta

    /**
     * Pega a conexão com o banco.(ESSENCIAL)
     */
    static Connection getConexao() throws SQLException {
        String urlCompleta = URL_DB + "?useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(urlCompleta, USER, PASSWORD);
    }

    /**
     * Ponto de Entrada (ESSENCIAL)
     */
    public static void main(String[] args) {

        InterfaceUsuario ui = new InterfaceUsuario();

        // --- Constantes do Menu ---
        final int CADASTRAR_USUARIO = 1;
        final int VER_USUARIOS = 2;
        final int DELETAR_USUARIO = 3;
        final int ATUALIZAR_USUARIO = 4;
        final int SAIR = 0;

        boolean ativo = true;

        while (ativo) {

            ui.exibirMenu(); //CHAMA O MENU
            int OPCAO_ESCOLHIDA = ui.lerOpcao(); //CHAMA A LEITURA DE OPÇÃO

            switch (OPCAO_ESCOLHIDA) {

                case CADASTRAR_USUARIO:

                    System.out.println("--- Cadastro de Novo Usuário ---");

                    // 1. Pedir o Nome (usando a UI)
                    String nome = ui.lerTexto("Digite o Nome: ");
                    // 2. Pedir o Email (usando a UI)
                    String email = ui.lerTexto("Digite o Email: ");
                    // 3. Pedir o Telefone (usando a UI)
                    long telefone = ui.lerLong("Digite o telefone: ");

                    // O 'try' agora é APENAS para o SQL, o que está correto.
                    try {
                        // 3. Tentar criar o usuário no banco
                        criarUsuario(nome, email, telefone);
                        System.out.println("\nUsuário '" + nome + "' cadastrado com sucesso!");

                    } catch (SQLException e) {
                        // Erro mais comum: Email duplicado
                        if (e.getErrorCode() == 1062) {
                            System.err.println("\nERRO: Este email já existe no banco de dados.");
                        } else {
                            // Outro erro de SQL (ex: banco offline, tabela não existe)
                            System.err.println("\nERRO de SQL: Falha ao cadastrar usuário.");
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        // Qualquer outro erro
                        System.err.println("\nERRO INESPERADO: " + e.getMessage());
                    }
                    break;

                case VER_USUARIOS:
                    verUsuario(); //
                    break;

                case DELETAR_USUARIO:
                    System.out.println("--- Deletar Usuário ---");
                    // 1. Pergunta qual ID deve ser deletado
                    long idParaDeletar = ui.lerLong("Digite o ID do usuário que deseja deletar: ");
                    // 2. Chama o metodo do DAO
                    deletarUsuario(idParaDeletar);
                    break;

                case ATUALIZAR_USUARIO:
                    System.out.println("--- Atualizar Usuário ---");

                    // 1. Pergunta QUAL usuário (o ID)
                    long idParaAtualizar = ui.lerLong("Digite o ID do usuário que deseja ATUALIZAR: ");

                    // 2. CHAMA O METODO DE VERIFICAÇÃO SE O USUÁRIO EXISTE
                    if (usuarioExiste(idParaAtualizar)) {

                        // 3. ID EXISTE! Agora sim, pede os NOVOS dados
                        System.out.println("Usuário encontrado! Agora, digite os NOVOS dados para o ID " + idParaAtualizar + ":");
                        String novoNome = ui.lerTexto("Digite o Novo Nome: ");
                        String novoEmail = ui.lerTexto("Digite o Novo Email: ");
                        long novoTelefone = ui.lerLong("Digite o Novo Telefone: ");

                        // 4. Chama o DAO (dentro de um try-catch)
                        try {
                            atualizarUsuario(idParaAtualizar, novoNome, novoEmail, novoTelefone);
                            // A mensagem de sucesso (LOG) já é impressa pelo DAO

                        } catch (SQLException e) {
                            if (e.getErrorCode() == 1062) {
                                System.err.println("\nERRO: Já existe outro usuário com esse email.");
                            } else {
                                System.err.println("\nERRO de SQL: Falha ao atualizar usuário.");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            System.err.println("\nERRO INESPERADO: " + e.getMessage());
                        }

                    } else {
                        // 5. ID NÃO EXISTE! Avisa e para.
                        System.err.println("\nERRO: Nenhum usuário encontrado com o ID " + idParaAtualizar + ".");
                    }
                    break;

                case SAIR:

                    System.out.println("Saindo do sistema...");
                    ativo = false; // Define 'ativo' como falso para ENCERRAR o loop 'while'
                    break;

                default:
                    // Se o usuário digitar -1 (erro do InputMismatch) ou qualquer outro número
                    System.err.println("Opção inválida. Tente novamente.");
            }

            if (OPCAO_ESCOLHIDA >= 1 && OPCAO_ESCOLHIDA <= 4) {
                ativo = ui.perguntarSeContinua();
            }
        }
        ui.fechar();
    }
}