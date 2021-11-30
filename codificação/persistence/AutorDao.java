package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Autor;

public class AutorDao implements IDaoAutor {
	
	/** Classe para tratar os dados do autor (cadastrar, pesquisar, alterar, deletar e listar).
	 * RF07-RF11: MANTER AUTOR
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */

	private Connection con;

	/**
	 * Conectar ao banco de dados
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public AutorDao() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		con = gDao.getConnection();
	}

	/**
	 * Inserir dados do autor
	 */
	@Override
	public void inserir(Autor autor) throws SQLException {
		String sql = "INSERT INTO Autor VALUES(?,?,?,?)";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, autor.getNome());
		ps.setString(2, autor.getDataNascimento());
		ps.setString(3, autor.getPais());
		ps.setString(4, autor.getBiografia());

		ps.execute();
		ps.close();
	}

	/**
	 * Pesquisar autor
	 */
	@Override
	public Autor pesquisar(Autor autor) throws SQLException {
		String sql = "SELECT codigo, nome, data_nascimento AS nascimento, pais, biografia FROM Autor WHERE codigo = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, autor.getCodigo());

		ResultSet rs = ps.executeQuery();
		int cout = 0;

		if (rs.next()) {
			autor.setCodigo(rs.getInt("codigo"));
			autor.setNome(rs.getString("nome"));
			autor.setDataNascimento(rs.getString("nascimento"));
			autor.setPais(rs.getString("pais"));
			autor.setBiografia(rs.getString("biografia"));
			cout++;
		}

		if (cout == 0) {
			autor = new Autor();
		}

		rs.close();
		ps.close();
		return autor;
	}

	/**
	 * Alterar autor
	 */
	@Override
	public void alterar(Autor autor) throws SQLException {
		String sql = "UPDATE Autor SET nome = ?, data_nascimento = ?, pais = ?, biografia = ? WHERE codigo = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, autor.getNome());
		ps.setString(2, autor.getDataNascimento());
		ps.setString(3, autor.getPais());
		ps.setString(4, autor.getBiografia());
		ps.setInt(5, autor.getCodigo());

		ps.execute();
		ps.close();
	}

	/**
	 * Deletar autor
	 */
	@Override
	public void excluir(Autor autor) throws SQLException {

		String sql = "DELETE Autor WHERE codigo = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, autor.getCodigo());

		ps.execute();
		ps.close();
	}

	/**
	 * Listar autores (todos)
	 */
	@Override
	public List<Autor> listar() throws SQLException {
		String sql = "SELECT codigo, nome, data_nascimento AS nascimento, pais, biografia FROM Autor ORDER BY nome ASC";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		List<Autor> autores = new ArrayList<Autor>();

		while (rs.next()) {
			Autor aut = new Autor();
			aut.setCodigo(rs.getInt("codigo"));
			aut.setNome(rs.getString("nome"));
			aut.setDataNascimento(rs.getString("nascimento"));
			aut.setPais(rs.getString("pais"));
			aut.setBiografia(rs.getString("biografia"));
			autores.add(aut);
		}

		rs.close();
		ps.close();
		return autores;
	}

	/**
	 * Listar autores segundo o filtro e a coluna
	 */
	@Override
	public List<Autor> listarPorColuna(String filtro, String coluna) throws SQLException {
		String sql = "";
		if (coluna.equalsIgnoreCase("nascimento")) {
			sql = "SELECT codigo, nome, data_nascimento AS nascimento, pais, biografia FROM Autor "
					+ "WHERE data_nascimento LIKE ? ORDER BY data_nascimento ASC";
		} else if (coluna.equalsIgnoreCase("selecione") && filtro.equals("")) {
			sql = "SELECT codigo, nome, data_nascimento AS nascimento, pais, biografia FROM Autor "
					+ "WHERE nome LIKE ? ORDER BY nome ASC";
		} else {
			sql = "SELECT codigo, nome, data_nascimento AS nascimento, pais, biografia FROM Autor " + "WHERE " + coluna
					+ " LIKE ? ORDER BY " + coluna + " ASC";
		}

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%" + filtro + "%");

		ResultSet rs = ps.executeQuery();

		List<Autor> autores = new ArrayList<Autor>();

		while (rs.next()) {
			Autor a = new Autor();
			a.setCodigo(rs.getInt("codigo"));
			a.setNome(rs.getString("nome"));
			a.setDataNascimento(rs.getString("nascimento"));
			a.setPais(rs.getString("pais"));
			a.setBiografia(rs.getString("biografia"));
			autores.add(a);
		}

		rs.close();
		ps.close();
		return autores;
	}

}
