package org.example;

import java.sql.*;

import static org.example.Main.getConexao;

public class UsuarioDao {  //UsuarioDao > Dao = Data Accses Object

    public static void criarUsuario(String nome, String email, long telefone) throws SQLException {
        String sql = "INSERT INTO tbl_usuario (nome, email, telefone) VALUES (?, ?, ?)";

        try (Connection conn = getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setLong(3, telefone);

            ps.executeUpdate();

        }
    }

    public static void verUsuario() {
        String sql = "SELECT * FROM tbl_usuario";

        try (Connection conn = getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int contador = 0;
            // O loop 'while (rs.next())' passa por cada linha que o banco retornou
            System.out.println("Usuários cadastrados:\n");
            while (rs.next()) {
                // Pega os dados de cada coluna da linha atual
                int id = rs.getInt("id_usuario");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                long telefone = rs.getLong("telefone");

                // Imprime os dados formatados
                System.out.printf("  ID: %d | Nome: %s | Email: %s | Telefone: %d%n", id, nome, email, telefone);
                contador++;
            }

            if (contador == 0) {
                System.out.println("  (Nenhum usuário encontrado no banco)");
            }

        } catch (SQLException e) {
            System.err.println("ERRO de SQL: Falha ao listar usuários.");
            e.printStackTrace();
        }
    }

    public static void deletarUsuario(long idParaDeletar) {
        String sql = "DELETE FROM tbl_usuario WHERE id_usuario = ?";

        // try-with-resources
        try (Connection conn = getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Define o ID que queremos apagar (substitui o '?')
            ps.setLong(1, idParaDeletar);

            // executeUpdate() retorna o número de linhas que foram afetadas
            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("LOG: Usuário com ID " + idParaDeletar + " deletado com sucesso.");
            } else {
                System.out.println("LOG: Nenhum usuário encontrado com o ID " + idParaDeletar + ".");
            }

        } catch (SQLException e) {
            System.err.println("ERRO de SQL: Falha ao deletar usuário.");
            e.printStackTrace();
        }
    }

    // 1. ADICIONE "throws SQLException" AQUI
    public static void atualizarUsuario(long idParaAtualizar, String novoNome, String novoEmail, long novoTelefone) throws SQLException {

        String sql = "UPDATE tbl_usuario SET nome = ?, email = ?, telefone = ? WHERE id_usuario = ?";

        // 2. O 'try' fica (ele é o try-with-resources, é essencial)
        try (Connection conn = getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Preenche os '?' do SET
            ps.setString(1, novoNome);
            ps.setString(2, novoEmail);
            ps.setLong(3, novoTelefone);

            // Preenche o '?' do WHERE
            ps.setLong(4, idParaAtualizar);

            // Executa e verifica
            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("LOG: Usuário com ID " + idParaAtualizar + " atualizado com sucesso.");
            } else {
                System.out.println("LOG: Nenhum usuário encontrado com o ID " + idParaAtualizar + ".");
            }
        }
    }
    public static boolean usuarioExiste(long id) {
        String sql = "SELECT 1 FROM tbl_usuario WHERE id_usuario = ? LIMIT 1";

        try (Connection conn = getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            // Tenta executar a consulta
            try (ResultSet rs = ps.executeQuery()) {
                // rs.next() retorna 'true' se o banco encontrou uma linha,
                // e 'false' se não encontrou nada.
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("ERRO de SQL: Falha ao verificar existência do usuário.");
            e.printStackTrace();
            return false; // Se der erro na consulta, assume que não existe.
        }
    }
}
