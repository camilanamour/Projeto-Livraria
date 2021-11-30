package controller;

import java.sql.SQLException;

import entity.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import persistence.IDaoLogin;
import persistence.LoginDao;

public class UsuarioController {
	
	/** Classe para o controle dos dados do usuário (cadastrar, buscar e recuperar).
	 * RNF02: SEGURANÇA
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
    public StringProperty nome = new SimpleStringProperty("");
    public StringProperty username = new SimpleStringProperty("");
    public StringProperty email = new SimpleStringProperty("");
    public StringProperty senha = new SimpleStringProperty("");
    public StringProperty confirmaSenha = new SimpleStringProperty("");
    
    private IDaoLogin lDao;
    private String nomeFuncionario;
    
    /**
     * Conectar com a persistencia do usuário
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public UsuarioController() throws ClassNotFoundException, SQLException {
		lDao = new LoginDao();
	}
    
    /**
     * Cadastrar usuário no sistema (dados pessoais)
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public void cadastrar() throws SQLException, IllegalAccessException{
    	if(senha.get().equals(confirmaSenha.get())){
    		Usuario usuario = toEntity();
    		lDao.cadastrar(usuario);
    	} else {
    		throw new IllegalAccessException("ERRO NA CONFIRMAÇÃO: Senhas diferentes.");
    	}
    	
    }
    
    /**
     * Recuperar os dados para login do usuário
     * @param email
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public void recuperar(String email) throws SQLException, IllegalAccessException{
    	if(!lDao.recuperar(email)){
    		throw new IllegalAccessException("E-MAIL NÃO ENCONTRADO: " + email);
    	}
    }
    
    /**
     * Valida o username e a senha para permissão de acesso 
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public boolean validar() throws SQLException, IllegalAccessException{
    	if(lDao.buscar(username.get(), senha.get())){
    		this.nomeFuncionario = lDao.getNome();
    		return true;
    	}else{
    		senha.set("");
    		throw new IllegalAccessException("SEM ACESSO: Usuário ou senha inválidos.");
    	}
    	
    }
    
    /**
     * Pega o primeiro nome do usuário que está logado
     * @return String - primeiro nome
     */
    public String getNomePrimeiro(){
    	String[] vetor = this.nomeFuncionario.split(" ");
		return vetor[0];
    }
    
    /**
     * Gera usuário
     * @return Usuario
     */
    public Usuario toEntity() {
        Usuario u = new Usuario();
        u.setNome(nome.get());
        u.setUsuario(username.get());
        u.setSenha(senha.get());
        u.setEmail(email.get());
        return u;
    }

    /**
     * Manda o Usuario para os campos
     * @param usuario
     */
    public void fromEntity(Usuario u) {
    	nome.set(u.getNome());
        username.set(u.getUsuario());
        senha.set(u.getSenha());
        email.set(u.getEmail());
    }
}
