package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.example.Main.getConexao;

public class UsuarioDao {  //UsuarioDao > Dao = Data Accses Object

    public static void criarUsuario(String nome, String email, long telefone) throws SQLException {
        String sql = "INSERT INTO tbl_usuario (nome, email, telefone) VALUES (?, ?, ?)";

        // try-with-resources fecha 'conn' e 'ps' automaticamente
        try (Connection conn = getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setLong(3, telefone);

            ps.executeUpdate();
            // Não precisamos mais do log "Criado usuário..." aqui,
            // pois o 'main' já dará a mensagem de sucesso.
        }
        // A 'throws SQLException' passa o erro para quem chamou (o 'main')
    }
}
