/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projetoPI.model;

/**
 *
 * @author teu_s
 */
public class Usuarios {
    private int idUsuario;
    private String nome;
    private String cargo;
    private String login;
    private String senhaHash;
    private String email;

    public Usuarios() {
    }
    
    
    
    public Usuarios(int idUsuario, String nome, String cargo, String login, String senhaHash, String email) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.cargo = cargo;
        this.login = login;
        this.senhaHash = senhaHash;
        this.email = email;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    public String getLogin() {
        return login;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public String getEmail() {
        return email;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
