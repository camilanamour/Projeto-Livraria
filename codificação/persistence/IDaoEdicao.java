package persistence;

import java.sql.SQLException;
import java.util.List;

import entity.Edicao;
import entity.Editora;
import entity.Livro;

public interface IDaoEdicao {
	public void inserir(Edicao e, Editora d, Livro l) throws SQLException;
	public Edicao pesquisar(String codigo) throws SQLException;
	public void alterar(Edicao e, Editora d, Livro l) throws SQLException;
	public void excluir(Edicao e) throws SQLException;
	public List<Edicao> listar() throws SQLException;
	public List<Edicao> listarPorColuna(String filtro, String coluna) throws SQLException;
}
