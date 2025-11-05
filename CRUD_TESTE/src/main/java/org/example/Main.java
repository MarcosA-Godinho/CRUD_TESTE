package org.example;

import java.util.Scanner;

// Imports do Banco de Dados
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.UsuarioDao.criarUsuario;

public class Main {

    // --- 1. Configurações do Banco de Dados ---
    private static final String URL_DB = "jdbc:mysql://localhost:3306/crud_java";
    private static final String USER = "root";

    // ATENÇÃO: Coloque sua senha do root aqui
    private static final String PASSWORD = "root";

    /**
     * Pega a conexão com o banco.(ESSENCIAL)
     */
    static Connection getConexao() throws SQLException {
        String urlCompleta = URL_DB + "?useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(urlCompleta, USER, PASSWORD);
    }

    /**
     * Ponto de Entrada: Agora interativo!(ESSENCIAL)
     */
    public static void main(String[] args) {

        while (ativo) {


            // O "try-with-resources" garante que o scanner será fechado no final
            try (Scanner scanner = new Scanner(System.in)) {

                System.out.println("--- Cadastro de Novo Usuário ---");

                // 1. Pedir o Nome
                System.out.print("Digite o Nome: ");
                String nome = scanner.nextLine();

                // 2. Pedir o Email (Obrigatório pela nossa tabela)
                System.out.print("Digite o Email: ");
                String email = scanner.nextLine();

                //3.Pedir o Telefone
                System.out.print("Digite o telefone:");
                long telefone = scanner.nextLong();

                // 3. Tentar criar o usuário no banco
                criarUsuario(nome, email, telefone);

                System.out.println("\nUsuário '" + nome + "' cadastrado com sucesso!");

            } catch (SQLException e) {
                // Erro mais comum: Email duplicado
                if (e.getErrorCode() == 1062) { // 1062 é o código de erro do MySQL para "Duplicate entry"
                    System.err.println("\nERRO: Este email já existe no banco de dados.");
                } else {
                    // Outro erro de SQL (ex: banco offline, tabela não existe)
                    System.err.println("\nERRO de SQL: Falha ao cadastrar usuário.");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                // Qualquer outro erro (ex: falha ao ler o scanner)
                System.err.println("\nERRO INESPERADO: " + e.getMessage());
            }
        }
    }

    /**
     * Metodo CREATE: Insere um novo usuário no banco.
     * (ESSENCIAL)
     */


    //
    // --- MÉTODOS APAGADOS ---
    //
    // O listarUsuarios() foi apagado.
    // O atualizarUsuario() foi apagado.
    // O deletarUsuario() foi apagado.
    //
}