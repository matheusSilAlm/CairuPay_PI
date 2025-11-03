/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projetoPI.dao;
import br.com.projetoPI.database.DbConnection;
import br.com.projetoPI.model.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 *
 * @author teu_s
 */
public class UsuarioDAO {
    /**
     * Busca um usuário no banco de dados pelo seu login.
     * @param login O login a ser pesquisado.
]     * @return Um objeto Usuario se encontrado, ou null se não encontrado.
     * @return 
     */
    
    public Usuarios getUsuarioPorLogin(String login) {
        
        String sql = "SELECT * FROM usuarios WHERE login = ?";
        Usuarios usuarioEncontrado = null;
        
        // O "try-with-resources" garante que a conexão (conn) e 
        // o PreparedStatement (ps) serão fechados automaticamente.
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, login);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Se encontrou, preenche o objeto Usuario
                    usuarioEncontrado = new Usuarios();
                    usuarioEncontrado.setIdUsuario(rs.getInt("id_usuario"));
                    usuarioEncontrado.setNome(rs.getString("nome"));
                    usuarioEncontrado.setCargo(rs.getString("cargo"));
                    usuarioEncontrado.setLogin(rs.getString("login"));
                    
                    // Atenção aqui: o requisito pedia "Senha"
                    // O seu banco tem "senha_hash"
                    // Estamos pegando o HASH do banco e colocando no atributo "senha" do model.
                    usuarioEncontrado.setSenhaHash(rs.getString("senha_hash")); 
                    
                    usuarioEncontrado.setEmail(rs.getString("email"));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por login: " + e.getMessage());
        }
        
        return usuarioEncontrado;
    }
}
