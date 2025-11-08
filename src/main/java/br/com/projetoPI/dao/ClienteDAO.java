/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projetoPI.dao;

import br.com.projetoPI.database.DbConnection;
import br.com.projetoPI.model.Clientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author teu_s
 */
public class ClienteDAO {
    
    public void inserirCliente(Clientes cliente) {
        String sql = "INSERT INTO clientes (nome_cliente, documento, endereco, uf, telefone, email) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cliente.getNomeCliente());
            ps.setString(2, cliente.getDocumento());
            ps.setString(3, cliente.getEndereco());
            ps.setString(4, cliente.getUf());
            ps.setString(5, cliente.getTelefone());
            ps.setString(6, cliente.getEmail());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
            throw new RuntimeException("Erro ao inserir cliente no banco de dados", e);
        }
    }

    /**
     * Atualiza os dados de um cliente existente no banco.
     * @param cliente O objeto Cliente com o ID e os dados atualizados.
     */
    public void atualizarCliente(Clientes cliente) {
        String sql = "UPDATE clientes SET nome_cliente = ?, documento = ?, endereco = ?, " +
                     "uf = ?, telefone = ?, email = ? WHERE id_cliente = ?";
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cliente.getNomeCliente());
            ps.setString(2, cliente.getDocumento());
            ps.setString(3, cliente.getEndereco());
            ps.setString(4, cliente.getUf());
            ps.setString(5, cliente.getTelefone());
            ps.setString(6, cliente.getEmail());
            ps.setInt(7, cliente.getIdCliente()); 
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar cliente no banco de dados", e);
        }
    }

    /**
     * Exclui um cliente do banco de dados com base no ID.
     * @param idCliente O ID do cliente a ser excluído.
     */
    public void excluirCliente(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            if (e.getErrorCode() == 1451) { // Código de erro do MySQL para FK constraint
                 System.err.println("Erro ao excluir cliente: Cliente possui dívidas associadas.");
                 throw new RuntimeException("Este cliente não pode ser excluído pois possui dívidas.", e);
            }
            
            System.err.println("Erro ao excluir cliente: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir cliente no banco de dados", e);
        }
    }

    /**
     * Lista todos os clientes cadastrados no banco.
     * @return Uma lista (List) de objetos Cliente.
     */
    public List<Clientes> listarTodosClientes() {
        String sql = "SELECT * FROM clientes";
        List<Clientes> clientes = new ArrayList<>();
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                clientes.add(mapearResultSetParaCliente(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
            throw new RuntimeException("Erro ao listar clientes do banco de dados", e);
        }
        
        return clientes;
    }

    /**
     * Busca um cliente específico pelo seu documento (CPF/CNPJ).
     * @param documento O documento a ser pesquisado.
     * @return O objeto Cliente se encontrado, ou null se não.
     */
    public Clientes buscarPorDocumentoCliente(String documento) {
        String sql = "SELECT * FROM clientes WHERE documento = ?";
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, documento);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaCliente(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por documento: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar cliente por documento", e);
        }
        
        return null; 
    }
    
    public Clientes buscarPorId(int idCliente) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaCliente(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente por ID", e);
        }
        return null;
    }

    /**
     * Método utilitário privado para converter uma linha do ResultSet em um objeto Cliente.
     * Evita repetição de código nos métodos de busca.
     * @param rs O ResultSet posicionado na linha correta.
     * @return Um objeto Cliente preenchido.
     * @throws SQLException
     */
    private Clientes mapearResultSetParaCliente(ResultSet rs) throws SQLException {
        Clientes cliente = new Clientes();
        cliente.setIdCliente(rs.getInt("id_cliente"));
        cliente.setNomeCliente(rs.getString("nome_cliente"));
        cliente.setDocumento(rs.getString("documento"));
        cliente.setEndereco(rs.getString("endereco"));
        cliente.setUf(rs.getString("uf"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setEmail(rs.getString("email"));
        return cliente;
    }

    
}
