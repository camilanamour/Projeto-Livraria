package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Categoria;

public class CategoriaDao{
	/** Classe para tratar os dados da categoria (cadastrar, pesquisar e listar).
	 * RF06: CADASTRAR CATEGORIA
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
	private Connection con;
	
	/**
	 * Conectar com o banco de dados
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public CategoriaDao() throws ClassNotFoundException, SQLException{
		GenericDao gDao = new GenericDao();
		con = gDao.getConnection();
	}
	
	/**
	 * Método para adicionar no banco de dados os dados da categoria
	 * @param cat - nome da categoria
	 * @throws SQLException
	 */
	public void adicionar(String cat) throws SQLException{
		String sql = "INSERT INTO Categoria VALUES (?)";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, cat);
		
		ps.execute();
		ps.close();
	}
	
	/**
	 * Método para buscar uma categoria pelo código
	 * @param codigo - código da categoria
	 * @return c - objeto categoria
	 * @throws SQLException
	 */
	public Categoria buscar(int codigo) throws SQLException{
		String sql = "SELECT codigo, nome FROM Categoria WHERE codigo = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, codigo);
		
		int cout = 0;
		Categoria c = new Categoria();
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			c.setCodigo(Integer.parseInt(rs.getString("codigo")));
			c.setNome(rs.getString("nome"));
			cout++;
		}
		
		if(cout == 0){
			throw new IllegalArgumentException("Escolha uma categoria");
		}
		
		rs.close();
		ps.close();
		return c;
	}
	
	/**
	 * Método para acessar a lista de todas as categorias no banco de dados
	 * @return categorias - lista de todas as categorias
	 * @throws SQLException
	 */
	public List<Categoria> listar() throws SQLException{
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		String sql = "SELECT codigo, nome FROM Categoria";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			Categoria cat = new Categoria();
			cat.setCodigo(rs.getInt("codigo"));
			cat.setNome(rs.getString("nome"));
			categorias.add(cat);
		}
		rs.close();
		ps.close();
		return categorias;
	}
}
