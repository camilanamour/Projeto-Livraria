package persistence;

import java.sql.SQLException;

import entity.Usuario;

public interface IDaoLogin {
	
	public void cadastrar(Usuario usuario) throws SQLException;
	public boolean buscar(String usuario, String senha) throws SQLException;
	public boolean recuperar(String email) throws SQLException;
	public String getNome();

}
