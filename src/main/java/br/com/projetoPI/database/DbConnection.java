/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projetoPI.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author teu_s
 */
public class DbConnection {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/CairuPay";
    private static final String TIMEZONE_CONFIG = "?serverTimezone=America/Sao_Paulo";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";
    
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            return DriverManager.getConnection(DATABASE_URL + TIMEZONE_CONFIG, USER, PASSWORD);
            
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados (SQLException): " + e.getMessage());
            throw new RuntimeException("Falha ao obter conexão com o banco de dados.", e);
            
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC do MySQL não encontrado: " + e.getMessage());
            throw new RuntimeException("Driver JDBC não encontrado. Verifique as bibliotecas do projeto.", e);
        }
    }
}
