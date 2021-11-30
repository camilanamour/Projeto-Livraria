package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.CategoriaController;
import entity.Autor;
import entity.Livro;

public class LivroDao implements IDaoLivro{
	
	private Connection con;
	private CategoriaController controlCat = new CategoriaController();
	
	/** Classe para tratar os dados do livro (cadastrar, pesquisar, alterar, deletar e listar).
	 * RF01-RN05: MANTER LIVRO
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
	public LivroDao() throws ClassNotFoundException, SQLException{
		GenericDao gDao = new GenericDao();
		con = gDao.getConnection();
	}

	/**
	 * Inserir Livro
	 */
	@Override
	public int inserir(Livro l) throws SQLException {
		String sql = "INSERT INTO Livro VALUES(?,?,?,?,?,?)";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, l.getTitulo());
		ps.setInt(2, l.getAno());
		ps.setString(3, l.getLingua());
		ps.setString(4, l.getFormato());
		ps.setString(5, l.getResumo());
		ps.setInt(6, l.getCategoria().getCodigo());
		
		ps.execute();
		ps.close();
		return this.getCodigo(l);
	}

	/**
	 * Pesquisar Livro
	 */
	@Override
	public Livro pesquisar(Livro l) throws SQLException {
		String sql = "SELECT id, titulo, ano, lingua, formato, resumo, categoria FROM Livro WHERE id = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, l.getCodigo());
		
		ResultSet rs = ps.executeQuery();
		int cout = 0;
		
		if (rs.next()){
			l.setCodigo(rs.getInt("id"));
			l.setTitulo(rs.getString("titulo"));
			l.setAno(rs.getInt("ano"));
			l.setLingua(rs.getString("lingua"));
			l.setFormato(rs.getString("formato"));
			l.setResumo(rs.getString("resumo"));
			l.setCategoria(controlCat.buscar(rs.getInt("categoria")));
			l.setAutores(this.listarAutores(l.getCodigo()));
			cout++;
		}
		
		if(cout == 0){
			l = new Livro();
		}
		
		rs.close();		
		ps.close();
		return l;
	}

	/**
	 * Alterar Livro
	 */
	@Override
	public void alterar(Livro l) throws SQLException {
		String sql = "UPDATE Livro SET titulo = ?, ano = ?, lingua = ?, formato = ?,"
				+ "resumo = ?, categoria = ? WHERE id = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, l.getTitulo());
		ps.setInt(2, l.getAno());
		ps.setString(3, l.getLingua());
		ps.setString(4, l.getFormato());
		ps.setString(5, l.getResumo());
		ps.setInt(6, l.getCategoria().getCodigo());
		ps.setInt(7, l.getCodigo());
		
		ps.execute();
		ps.close();
		
		this.removerAssociativa(l.getCodigo());
		
		int tamanho = l.getAutores().size();
		for(int i=0; i<tamanho; i++){
			this.inserirAssociativa(l.getAutores().get(i).getCodigo(), l.getCodigo());
		}
	}

	/**
	 * Deletar Livro
	 */
	@Override
	public void excluir(Livro l) throws SQLException {
		
		this.removerAssociativa(l.getCodigo());
		
		String sql = "DELETE Livro WHERE id = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, l.getCodigo());
		
		ps.execute();
		ps.close();
		
	}

	/**
	 * Lista livros (todos)
	 */
	@Override
	public List<Livro> listar() throws SQLException {
		String sql = "SELECT id, titulo, ano, lingua, formato, resumo, categoria "
				+ "FROM Livro ORDER BY titulo ASC";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		List<Livro> livros = new ArrayList<Livro>();
		
		while (rs.next()){
			Livro l = new Livro();
			l.setCodigo(rs.getInt("id"));
			l.setTitulo(rs.getString("titulo"));
			l.setAno(rs.getInt("ano"));
			l.setLingua(rs.getString("lingua"));
			l.setFormato(rs.getString("formato"));
			l.setResumo(rs.getString("resumo"));
			l.setCategoria(controlCat.buscar(rs.getInt("categoria")));
			l.setAutores(this.listarAutores(l.getCodigo()));
			livros.add(l);
		}
		
		rs.close();		
		ps.close();
		return livros;
	}
	
	/**
	 * Listar os livros pelo filtro e pela coluna
	 */
	public List<Autor> listarAutores(int livro) throws SQLException {
		
		String sql ="SELECT Autor.codigo AS cod, Autor.nome AS nome " +
					"FROM Autor INNER JOIN Livro_Autor " +
					"ON Autor.codigo = Livro_Autor.id_autor " +
					"INNER JOIN Livro " +
					"ON Livro.id = Livro_Autor.id_livro " +
					"WHERE Livro.id = ? " +
					"ORDER BY Autor.nome ASC";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, livro);
		
		ResultSet rs = ps.executeQuery();
		
		List<Autor> autores = new ArrayList<Autor>();
		
		while (rs.next()){
			Autor aut = new Autor();
			aut.setCodigo(rs.getInt("cod"));
			aut.setNome(rs.getString("nome"));
			autores.add(aut);
		}
		
		rs.close();		
		ps.close();
		return autores;
	}
	
	/**
	 * Inserir a associativa entre livro e autor
	 */
	public void inserirAssociativa(int autor, int livro) throws SQLException{
		String sql = "INSERT INTO Livro_Autor VALUES (?,?)";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, livro);
		ps.setInt(2, autor);
		
		ps.execute();
		ps.close();
	}
	
	/**
	 * Remover a associativa entre livro e autor
	 */
	public void removerAssociativa(int livro) throws SQLException{
		String sql = "DELETE Livro_Autor WHERE id_livro = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, livro);
		
		ps.execute();
		ps.close();
	}
	
	/**
	 * Código do livro
	 * @param l - Livro
	 * @return codigo do livro
	 * @throws SQLException
	 */
	private int getCodigo(Livro l) throws SQLException{
		String sqlCodigo = "SELECT id FROM Livro WHERE titulo = ? AND resumo = ?";
		
		PreparedStatement ps = con.prepareStatement(sqlCodigo);
		ps.setString(1, l.getTitulo());
		ps.setString(2, l.getResumo());
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()){
			l.setCodigo(rs.getInt("id"));
		}
		
		ps.execute();
		ps.close();
		return l.getCodigo();
	}

	/**
	 * Listar livros pelo filtro e pela coluna
	 */
	@Override
	public List<Livro> listarPorColuna(String filtro, String coluna) throws SQLException {

		String sql = "";
		if(coluna.equalsIgnoreCase("categoria")){
			sql = "SELECT Livro.id AS id, Livro.titulo AS titulo, Livro.ano AS ano, Livro.lingua AS lingua, " +
					"Livro.formato AS formato, Livro.resumo AS resumo, Livro.categoria AS categoria " +
					"FROM Livro INNER JOIN Categoria " +
					"ON Livro.categoria = Categoria.codigo " +
					"WHERE Categoria.nome LIKE ? " +
					"ORDER BY Categoria.nome ASC";
			
		}else if(coluna.equalsIgnoreCase("autor")){
			sql = "SELECT Livro.id AS id, Livro.titulo AS titulo, Livro.ano AS ano, Livro.lingua AS lingua, " +
					"Livro.formato AS formato, Livro.resumo AS resumo, Livro.categoria AS categoria " +
					"FROM Livro INNER JOIN Livro_Autor " +
					"ON Livro.id = Livro_Autor.id_livro " +
					"INNER JOIN Autor " +
					"ON Autor.codigo = Livro_Autor.id_autor " +
					"WHERE Autor.nome LIKE ? "+
					"ORDER BY Autor.nome ASC";
			
		}else if(coluna.equalsIgnoreCase("selecione") && filtro.equals("")){
			sql = "SELECT id, titulo, ano, lingua, formato, resumo, categoria "
					+ "FROM Livro WHERE titulo LIKE ? ORDER BY titulo ASC";
		}else{
			sql = "SELECT id, titulo, ano, lingua, formato, resumo, categoria "
					+ "FROM Livro WHERE " + coluna + " LIKE ? ORDER BY " + coluna + " ASC";
		}
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%" + filtro + "%");
		
		ResultSet rs = ps.executeQuery();
		
		List<Livro> livros = new ArrayList<Livro>();
		
		while (rs.next()){
			Livro l = new Livro();
			l.setCodigo(rs.getInt("id"));
			l.setTitulo(rs.getString("titulo"));
			l.setAno(rs.getInt("ano"));
			l.setLingua(rs.getString("lingua"));
			l.setFormato(rs.getString("formato"));
			l.setResumo(rs.getString("resumo"));
			l.setCategoria(controlCat.buscar(rs.getInt("categoria")));
			l.setAutores(this.listarAutores(l.getCodigo()));
			livros.add(l);
		}
		
		rs.close();		
		ps.close();
		return livros;
	}
}
