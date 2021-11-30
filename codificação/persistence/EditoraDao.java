package persistence;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Editora;

public class EditoraDao implements IDaoEditora{
	
	/** Classe para tratar os dados da editora (cadastrar, pesquisar, alterar, deletar e listar).
	 * RF12-RN16: MANTER EDITORA
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
	private Connection con;
	
	/**
	 * Conectar o banco de dados
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public EditoraDao() throws ClassNotFoundException, SQLException{
		GenericDao gDao = new GenericDao();
		con = gDao.getConnection();
	}

	/**
	 * Inserir Editora
	 */
	@Override
	public void inserir(Editora e) throws SQLException {
		String sql = "INSERT INTO Editora VALUES(?,?,?,?,?,?,?,?)";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getCnpj());
		ps.setString(2, e.getNome());
		ps.setString(3, e.getEmail());
		ps.setString(4, e.getTelefone());
		ps.setString(5, e.getLogradouro());
		ps.setInt(6, e.getNumero());
		ps.setString(7, e.getComplemento());
		ps.setString(8, e.getCep());
		
		ps.execute();
		ps.close();
	}

	/**
	 * Pesquisar Editora 
	 */
	@Override
	public Editora pesquisar(Editora e) throws SQLException {
		String sql = "SELECT SUBSTRING(cnpj, 1, 2) + '.' + SUBSTRING(cnpj, 3, 3) + '.' + SUBSTRING(cnpj, 6, 3)" 
				+ "+ '/' + SUBSTRING(cnpj, 9, 4) + '-' + SUBSTRING(cnpj, 13, 2) AS cnpj, nome, email, SUBSTRING(telefone, 1, 4) + '-' +"
				+ "SUBSTRING(telefone, 5, 4) AS telefone, logradouro_end, numero_end, complemento_end, SUBSTRING(cep, 1, 5) + '-' + SUBSTRING(telefone, 6, 3) AS cep "
				+ "FROM Editora WHERE cnpj LIKE ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getCnpj());
		
		ResultSet rs = ps.executeQuery();
		int cout = 0;
		
		if (rs.next()){
			e.setCnpj(rs.getString("cnpj"));
			e.setNome(rs.getString("nome"));
			e.setEmail(rs.getString("email"));
			e.setTelefone(rs.getString("telefone"));
			e.setLogradouro(rs.getString("logradouro_end"));
			e.setNumero(rs.getInt("numero_end"));
			e.setComplemento(rs.getString("complemento_end"));
			e.setCep(rs.getString("cep"));

			cout++;
		}
		
		if(cout == 0){
			e = new Editora();
		}
		
		rs.close();		
		ps.close();
		return e;
	}

	/**
	 * Alterar Editora
	 */
	@Override
	public void alterar(Editora e) throws SQLException {
		String sql = "UPDATE Editora SET cnpj = ?, nome = ?, email = ?, telefone = ?, "
				+ "logradouro_end = ?, numero_end = ?, complemento_end = ?, cep = ? WHERE cnpj = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getCnpj());
		ps.setString(2, e.getNome());
		ps.setString(3, e.getEmail());
		ps.setString(4, e.getTelefone());
		ps.setString(5, e.getLogradouro());
		ps.setInt(6, e.getNumero());
		ps.setString(7, e.getComplemento());
		ps.setString(8, e.getCep());
		ps.setString(9, e.getCnpj());
		
		ps.execute();
		ps.close();
		
	}

	/**
	 * Deletar Editora
	 */
	@Override
	public void excluir(Editora e) throws SQLException {
		String sql = "DELETE Editora WHERE cnpj = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getCnpj());
		
		ps.execute();
		ps.close();
		
	}

	/**
	 * Listar editoras (todas)
	 */
	@Override
	public List<Editora> listar() throws SQLException {
		String sql = "SELECT SUBSTRING(cnpj, 1, 2) + '.' + SUBSTRING(cnpj, 3, 3) + '.' + SUBSTRING(cnpj, 6, 3)" 
				+ "+ '/' + SUBSTRING(cnpj, 9, 4) + '-' + SUBSTRING(cnpj, 13, 2) AS cnpj, nome, email, SUBSTRING(telefone, 1, 4) + '-' +"
				+ "SUBSTRING(telefone, 5, 4) AS telefone, logradouro_end, numero_end, complemento_end, SUBSTRING(cep, 1, 5) + '-' + SUBSTRING(telefone, 6, 3) AS cep "
				+ "FROM Editora";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		List<Editora> editoras = new ArrayList<Editora>();
		
		while (rs.next()){
			Editora e = new Editora();
			e.setCnpj(rs.getString("cnpj"));
			e.setNome(rs.getString("nome"));
			e.setEmail(rs.getString("email"));
			e.setTelefone(rs.getString("telefone"));
			e.setLogradouro(rs.getString("logradouro_end"));
			e.setNumero(rs.getInt("numero_end"));
			e.setComplemento(rs.getString("complemento_end"));
			e.setCep(rs.getString("cep"));
			editoras.add(e);
		}
		
		rs.close();		
		ps.close();
		return editoras;
	}
	
	/**
	 * Listar editoras pelo filtro e a coluna
	 */
	@Override
	public List<Editora> listarPorColuna(String filtro, String coluna) throws SQLException {

		String sql = "";
		
		if(coluna.equalsIgnoreCase("selecione") && filtro.equals("")){
			sql = "SELECT SUBSTRING(cnpj, 1, 2) + '.' + SUBSTRING(cnpj, 3, 3) + '.' + SUBSTRING(cnpj, 6, 3)" 
					+ "+ '/' + SUBSTRING(cnpj, 9, 4) + '-' + SUBSTRING(cnpj, 13, 2) AS cnpj, nome, email, SUBSTRING(telefone, 1, 4) + '-' +"
					+ "SUBSTRING(telefone, 5, 4) AS telefone, logradouro_end, numero_end, complemento_end, SUBSTRING(cep, 1, 5) + '-' + SUBSTRING(telefone, 6, 3) AS cep " 
					+ "FROM Editora WHERE nome LIKE ? ORDER BY nome ASC";
		}else{
			sql = "SELECT SUBSTRING(cnpj, 1, 2) + '.' + SUBSTRING(cnpj, 3, 3) + '.' + SUBSTRING(cnpj, 6, 3)" 
					+ "+ '/' + SUBSTRING(cnpj, 9, 4) + '-' + SUBSTRING(cnpj, 13, 2) AS cnpj, nome, email, SUBSTRING(telefone, 1, 4) + '-' +"
					+ "SUBSTRING(telefone, 5, 4) AS telefone, logradouro_end, numero_end, complemento_end, SUBSTRING(cep, 1, 5) + '-' + SUBSTRING(telefone, 6, 3) AS cep " 
					+ "FROM Editora WHERE " + coluna + " LIKE ? ORDER BY "+ coluna + " ASC";
		}
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%" + filtro + "%");
		
		ResultSet rs = ps.executeQuery();
		
		List<Editora> editoras = new ArrayList<Editora>();
		
		while (rs.next()){
			Editora e = new Editora();
			e.setCnpj(rs.getString("cnpj"));
			e.setNome(rs.getString("nome"));
			e.setEmail(rs.getString("email"));
			e.setTelefone(rs.getString("telefone"));
			e.setLogradouro(rs.getString("logradouro_end"));
			e.setNumero(rs.getInt("numero_end"));
			e.setComplemento(rs.getString("complemento_end"));
			e.setCep(rs.getString("cep"));
			editoras.add(e);
		}
		
		rs.close();		
		ps.close();
		return editoras;
	}
	
	/**
	 * Pesquisa o CNPJ
	 * @param e - editora
	 * @return cnpj - o CNPJ
	 * @throws SQLException
	 */
	private String getCNPJ(Editora e) throws SQLException{
		String sqlCodigo = "SELECT cnpj FROM Editora WHERE nome = ?";
		
		PreparedStatement ps = con.prepareStatement(sqlCodigo);
		ps.setString(1, e.getNome());
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()){
			e.setCnpj(rs.getString("cnpj"));
		}
		
		ps.execute();
		ps.close();
		return e.getCnpj();
	}
}
