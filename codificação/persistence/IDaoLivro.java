package persistence;

import java.sql.SQLException;
import java.util.List;

import entity.Autor;
import entity.Livro;

public interface IDaoLivro{
	public int inserir(Livro tipo) throws SQLException;
	public Livro pesquisar(Livro tipo) throws SQLException;
	public void alterar(Livro tipo) throws SQLException;
	public void excluir(Livro tipo) throws SQLException;
	public List<Livro> listar() throws SQLException;
	public List<Livro> listarPorColuna(String filtro, String coluna) throws SQLException;
	public List<Autor> listarAutores(int i) throws SQLException;
	public void inserirAssociativa(int autor, int livro) throws SQLException;
	public void removerAssociativa(int livro) throws SQLException;
}
