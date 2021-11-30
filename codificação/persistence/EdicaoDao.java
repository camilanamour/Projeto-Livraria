package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.CategoriaController;
import entity.Edicao;
import entity.Editora;
import entity.Livro;

public class EdicaoDao implements IDaoEdicao {
	
	/** Classe para tratar os dados da edição (cadastrar, pesquisar, alterar, deletar e listar).
	 * RF17-RN21: MANTER EDICAO
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
	private Connection con;
	private CategoriaController controlCat = new CategoriaController();
	
	/**
	 * Conectar Banco de dados
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public EdicaoDao() throws ClassNotFoundException, SQLException{
		GenericDao gDao = new GenericDao();
		con = gDao.getConnection();
	}
	
	/**
	 * Inserir Edição
	 */
	@Override
	public void inserir(Edicao e, Editora d, Livro l) throws SQLException {
		String sql = "INSERT INTO Edicao VALUES (?, ?, ?, ?, ?) INSERT INTO Livro_Editora_Edicao VALUES (?, ?, ?)";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getIsbn());
		ps.setInt(2, e.getAno());
		ps.setInt(3, e.getNumeroPaginas());
		ps.setInt(4, e.getQuantidadeEstoque());
		ps.setDouble(5, e.getValorUnitario());
		ps.setInt(6, l.getCodigo());
		ps.setString(7, d.getCnpj());
		ps.setString(8, e.getIsbn());
		
		ps.execute();
		ps.close();
		
	}

	/**
	 * Alterar Edição
	 */
	@Override
	public void alterar(Edicao e, Editora d, Livro l) throws SQLException {
		
		String sql = "UPDATE Edicao SET isbn = ?, ano = ?, numero_paginas = ?, qtd_estoque = ?,"
				+ "valor_unitario = ? WHERE isbn = ? UPDATE Livro_Editora_Edicao SET id_livro = ?, id_editora = ?, id_edicao = ?"
				+ " WHERE id_edicao = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getIsbn());
		ps.setInt(2, e.getAno());
		ps.setInt(3, e.getNumeroPaginas());
		ps.setInt(4, e.getQuantidadeEstoque());
		ps.setDouble(5, e.getValorUnitario());
		ps.setString(6, e.getIsbn());
		ps.setInt(7, l.getCodigo());
		ps.setString(8, d.getCnpj());
		ps.setString(9, e.getIsbn());
		ps.setString(10, e.getIsbn());
		
		ps.execute();
		ps.close();
		
	}

	/**
	 * Deletar Edição
	 */
	@Override
	public void excluir(Edicao e) throws SQLException {
		String sql = "DELETE Livro_Editora_Edicao WHERE id_edicao = ? DELETE Edicao WHERE isbn = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getIsbn());
		ps.setString(2, e.getIsbn());
		
		ps.execute();
		ps.close();	
		
	}

	/**
	 * Pesquisar Edição
	 */
	@Override
	public Edicao pesquisar(String codigo) throws SQLException {
		String sql = "SELECT * FROM Edicao e, Editora d, Livro l, Livro_Editora_Edicao lee "
	+ "WHERE e.isbn = lee.id_edicao AND d.cnpj = lee.id_editora AND l.id = lee.id_livro AND isbn = ? ";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, codigo);
		
		ResultSet rs = ps.executeQuery();
		int cout = 0;
		Edicao e = new Edicao();
		
		if (rs.next()){
			Editora d = new Editora();
			Livro l = new Livro();
			d.setCnpj(rs.getString("cnpj"));
			d.setNome(rs.getString("nome"));
			d.setEmail(rs.getString("email"));
			d.setTelefone(rs.getString("telefone"));
			d.setLogradouro(rs.getString("logradouro_end"));
			d.setNumero(rs.getInt("numero_end"));
			d.setComplemento(rs.getString("complemento_end"));
			d.setCep(rs.getString("cep"));
			l.setCodigo(rs.getInt("id"));
			l.setTitulo(rs.getString("titulo"));
			l.setAno(rs.getInt("ano"));
			l.setLingua(rs.getString("lingua"));
			l.setFormato(rs.getString("formato"));
			l.setResumo(rs.getString("resumo"));
			l.setCategoria(controlCat.buscar(rs.getInt("categoria")));
			e.setIsbn(rs.getString("isbn"));
			e.setEditora(d);
			e.setLivro(l);
			e.setAno(rs.getInt("ano"));
			e.setNumeroPaginas(rs.getInt("numero_paginas"));
			e.setValorUnitario(rs.getDouble("valor_unitario"));
			e.setQuantidadeEstoque(rs.getInt("qtd_estoque"));

			cout++;
		}
		
		if(cout == 0){
			e = new Edicao();
		}
		
		rs.close();		
		ps.close();
		return e;
	}

	/**
	 * Listar edições (todos)
	 */
	@Override
	public List<Edicao> listar() throws SQLException {
		String sql = "SELECT * FROM Edicao e, Editora d, Livro l, Livro_Editora_Edicao lee "
		+ "WHERE e.isbn = lee.id_edicao AND d.cnpj = lee.id_editora AND l.id = lee.id_livro";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		List<Edicao> edicoes = new ArrayList<Edicao>();
		
		while (rs.next()){
			Editora d = new Editora();
			Edicao e = new Edicao();
			Livro l = new Livro();
			d.setCnpj(rs.getString("cnpj"));
			d.setNome(rs.getString("nome"));
			d.setEmail(rs.getString("email"));
			d.setTelefone(rs.getString("telefone"));
			d.setLogradouro(rs.getString("logradouro_end"));
			d.setNumero(rs.getInt("numero_end"));
			d.setComplemento(rs.getString("complemento_end"));
			d.setCep(rs.getString("cep"));
			l.setCodigo(rs.getInt("id"));
			l.setTitulo(rs.getString("titulo"));
			l.setAno(rs.getInt("ano"));
			l.setLingua(rs.getString("lingua"));
			l.setFormato(rs.getString("formato"));
			l.setResumo(rs.getString("resumo"));
			l.setCategoria(controlCat.buscar(rs.getInt("categoria")));
			e.setIsbn(rs.getString("isbn"));
			e.setEditora(d);
			e.setLivro(l);
			e.setAno(rs.getInt("ano"));
			e.setNumeroPaginas(rs.getInt("numero_paginas"));
			e.setValorUnitario(rs.getDouble("valor_unitario"));
			e.setQuantidadeEstoque(rs.getInt("qtd_estoque"));
			edicoes.add(e);
		}
		
		rs.close();		
		ps.close();
		return edicoes;
	}

	/**
	 * Listar edições pelo filtro e coluna
	 */
	@Override
	public List<Edicao> listarPorColuna(String filtro, String coluna) throws SQLException {
		
		String sql = "";
		
		if(coluna.equalsIgnoreCase("selecione")){
			sql = "SELECT * FROM Edicao e, Editora d, Livro l, Livro_Editora_Edicao lee "
					+"WHERE e.isbn = lee.id_edicao AND d.cnpj = lee.id_editora AND l.id = lee.id_livro" 
					+ " AND l.titulo LIKE ? ORDER BY l.titulo ASC";
		}else if(coluna.equalsIgnoreCase("livro")){
			sql = "SELECT * FROM Edicao e, Editora d, Livro l, Livro_Editora_Edicao lee "
					+"WHERE e.isbn = lee.id_edicao AND d.cnpj = lee.id_editora AND l.id = lee.id_livro" 
					+ " AND l.titulo LIKE ? ORDER BY l.titulo ASC";
		}else if(coluna.equalsIgnoreCase("editora")){
			sql = "SELECT * FROM Edicao e, Editora d, Livro l, Livro_Editora_Edicao lee "
					+"WHERE e.isbn = lee.id_edicao AND d.cnpj = lee.id_editora AND l.id = lee.id_livro" 
					+ " AND d.nome LIKE ? ORDER BY d.nome ASC";
		}else{
			sql = "SELECT * FROM Edicao e, Editora d, Livro l, Livro_Editora_Edicao lee "
					+"WHERE e.isbn = lee.id_edicao AND d.cnpj = lee.id_editora AND l.id = lee.id_livro" 
					+ " AND e." + coluna + " LIKE ? ORDER BY e."+ coluna + " ASC";
		}
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%" + filtro + "%");
		
		ResultSet rs = ps.executeQuery();
		
		List<Edicao> edicoes = new ArrayList<Edicao>();
		
		while (rs.next()){
			Editora d = new Editora();
			Edicao e = new Edicao();
			Livro l = new Livro();
			d.setCnpj(rs.getString("cnpj"));
			d.setNome(rs.getString("nome"));
			d.setEmail(rs.getString("email"));
			d.setTelefone(rs.getString("telefone"));
			d.setLogradouro(rs.getString("logradouro_end"));
			d.setNumero(rs.getInt("numero_end"));
			d.setComplemento(rs.getString("complemento_end"));
			d.setCep(rs.getString("cep"));
			l.setCodigo(rs.getInt("id"));
			l.setTitulo(rs.getString("titulo"));
			l.setAno(rs.getInt("ano"));
			l.setLingua(rs.getString("lingua"));
			l.setFormato(rs.getString("formato"));
			l.setResumo(rs.getString("resumo"));
			l.setCategoria(controlCat.buscar(rs.getInt("categoria")));
			e.setIsbn(rs.getString("isbn"));
			e.setEditora(d);
			e.setLivro(l);
			e.setAno(rs.getInt("ano"));
			e.setNumeroPaginas(rs.getInt("numero_paginas"));
			e.setValorUnitario(rs.getDouble("valor_unitario"));
			e.setQuantidadeEstoque(rs.getInt("qtd_estoque"));
			edicoes.add(e);
		}
		
		rs.close();		
		ps.close();
		return edicoes;
	}

}
