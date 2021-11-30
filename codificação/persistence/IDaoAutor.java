package persistence;

import java.sql.SQLException;
import java.util.List;

import entity.Autor;

public interface IDaoAutor {
	
	public void inserir(Autor tipo) throws SQLException;
	public Autor pesquisar(Autor tipo) throws SQLException;
	public void alterar(Autor tipo) throws SQLException;
	public void excluir(Autor tipo) throws SQLException;
	public List<Autor> listar() throws SQLException;
	public List<Autor> listarPorColuna(String filtro, String coluna) throws SQLException;

}
