/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projetoPI.dao;


import br.com.projetoPI.database.DbConnection;
import br.com.projetoPI.model.Dividas;
import br.com.projetoPI.model.Clientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author teu_s
 */
public class DividaDAO {
    
    private ClienteDAO clienteDAO;
    
    public DividaDAO() {
        this.clienteDAO = new ClienteDAO(); // Instancia o DAO dependente
    }
    
    public void inserirDivida(Dividas divida){
        String sql = "INSERT INTO dividas (id_credor, id_devedor, valor_divida, data_atualizacao) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, divida.getCredor().getIdCliente());
            ps.setInt(2, divida.getDevedor().getIdCliente());
            ps.setBigDecimal(3, divida.getValorDivida());
            ps.setDate(4, new java.sql.Date(divida.getDataAtualizacao().getTime()));
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir dívida", e);
        }
    }
    
    public void excluirDivida(int idDivida){
        String sql = "DELETE FROM dividas WHERE id_divida = ?";
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idDivida);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            // Requisito Funcional: Só poderá excluir uma divida se não existir pagamento
            if (e.getErrorCode() == 1451) { // Erro de FK do MySQL
                throw new RuntimeException("Não é possível excluir: Dívida possui pagamento associado.", e);
            }
            throw new RuntimeException("Erro ao excluir dívida", e);
        }
    }
    
    public Dividas buscarPorId(int idDivida) {
        String sql = "SELECT * FROM dividas WHERE id_divida = ?";
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idDivida);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaDivida(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar dívida por ID", e);
        }
        return null;
    }
    
    
    public boolean clientePossuiDividas(int idCliente) {
        String sql = "SELECT COUNT(*) FROM dividas WHERE id_credor = ? OR id_devedor = ?";
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);
            ps.setInt(2, idCliente);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true se a contagem for > 0
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar dívidas do cliente", e);
        }
        return false;
    }
    
    
    public List<Dividas> listarNaoPagas() {
        // Usa LEFT JOIN para encontrar dívidas sem correspondência em pagamentos
        String sql = "SELECT d.* FROM dividas d " +
                     "LEFT JOIN pagamentos p ON d.id_divida = p.id_divida " +
                     "WHERE p.id_pagamento IS NULL";
        
        List<Dividas> dividas = new ArrayList<>();
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                dividas.add(mapearResultSetParaDivida(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar dívidas não pagas", e);
        }
        return dividas;
    }
    
    
    public List<Dividas> listarPorDocumentoDevedor(String documento) {
        // Usa JOIN com clientes para filtrar pelo documento
        String sql = "SELECT d.* FROM dividas d " +
                     "JOIN clientes c ON d.id_devedor = c.id_cliente " +
                     "WHERE c.documento = ?";

        List<Dividas> divida = new ArrayList<>();
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, documento);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    divida.add(mapearResultSetParaDivida(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar dívidas por documento", e);
        }
        return divida;
    }
    
    
    /**
     * Método "Mágico" (Helper)
     * Converte uma linha do ResultSet (com IDs) em um objeto Divida (com objetos Cliente).
     */
    private Dividas mapearResultSetParaDivida(ResultSet rs) throws SQLException {
        Dividas divida = new Dividas();
        divida.setIdDivida(rs.getInt("id_divida"));

        int idCredor = rs.getInt("id_credor");
        int idDevedor = rs.getInt("id_devedor");
        
        // USA O CLIENTE DAO para "hidratar" os objetos
        // (Buscar o Cliente completo a partir do ID)
        Clientes credor = clienteDAO.buscarPorId(idCredor); 
        Clientes devedor = clienteDAO.buscarPorId(idDevedor);
        
        divida.setCredor(credor);
        divida.setDevedor(devedor);
        
        divida.setValorDivida(rs.getBigDecimal("valor_divida"));
        divida.setDataAtualizacao(rs.getDate("data_atualizacao"));
        
        return divida;
    }
    
    
    
    
}
