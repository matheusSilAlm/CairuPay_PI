/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projetoPI.database;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author teu_s
 */
public class TestConexao {
    public static void main(String[] args) {
        
        System.out.println("Iniciando teste de conexão...");

        Connection conexao = null;
        try {
            // 1. Tenta pegar a conexão da sua fábrica
            conexao = DbConnection.getConnection();
            
            // 2. Se a linha acima não falhar, a conexão foi bem-sucedida!
            System.out.println("-----------------------------------------");
            System.out.println("SUCESSO: Conexão com o banco estabelecida!");
            System.out.println("-----------------------------------------");

        } catch (RuntimeException e) {
            // 3. Se falhar (ex: senha errada, banco offline, nome do banco errado)
            System.err.println("-----------------------------------------");
            System.err.println("FALHA: Não foi possível conectar ao banco.");
            System.err.println("-----------------------------------------");
            
            // Imprime o erro completo (stack trace) para vermos o motivo
            e.printStackTrace();
            
        } finally {
            // 4. (Boa prática) Garante que a conexão seja fechada se foi aberta
            if (conexao != null) {
                try {
                    conexao.close();
                    System.out.println("Conexão fechada com sucesso.");
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar a conexão: " + e.getMessage());
                }
            }
        }
    }
}
