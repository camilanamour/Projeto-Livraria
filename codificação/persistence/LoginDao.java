package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Usuario;

public class LoginDao implements IDaoLogin{
	
	/** Classe para armazenar os dados do usuário (cadastrar, buscar e recuperar).
	 * RNF02: SEGURANÇA
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
	private Connection con;
	private String usuarioAcesso;
	
	/**
	 * Conectar no banco de dados
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public LoginDao() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		con = gDao.getConnection();
	}
	
	/**
	 * Nome do usuário
	 */
	public String getNome(){
		return usuarioAcesso;
	}

	/**
	 * Inserir usuário no banco de dados
	 */
	@Override
	public void cadastrar(Usuario usuario) throws SQLException {
		String sql = "INSERT INTO Usuario VALUES(?,?,?,?)";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, usuario.getNome());
		ps.setString(2, usuario.getUsuario());
		ps.setString(3, usuario.getSenha());
		ps.setString(4, usuario.getEmail());
		
		ps.execute();
		ps.close();
		
	}

	/**
	 * Buscar os dados no banco de dados pelo usuário e senha
	 */
	@Override
	public boolean buscar(String usuario, String senha) throws SQLException {
		String sql = "SELECT username, senha, nome FROM Usuario";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()){
			if(rs.getString("username").equals(usuario) && rs.getString("senha").equals(senha)){
				this.usuarioAcesso = rs.getString("nome");
				return true;
			}
		}
		return false;		
	}
	
	/**
	 * Verifica se o e-mail está no sistema
	 */
	@Override
	public boolean recuperar(String email) throws SQLException {
			String sql = "SELECT email FROM Usuario";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()){
				if(rs.getString("email").equals(email)){
					return true;
				}
			}
			return false;
	}

}
