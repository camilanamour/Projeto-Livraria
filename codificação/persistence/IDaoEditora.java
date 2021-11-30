package persistence;

import java.sql.SQLException;
import java.util.List;

import entity.Editora;

public interface IDaoEditora {
	public void inserir(Editora tipo) throws SQLException;
	public Editora pesquisar(Editora tipo) throws SQLException;
	public void alterar(Editora tipo) throws SQLException;
	public void excluir(Editora tipo) throws SQLException;
	public List<Editora> listar() throws SQLException;
	public List<Editora> listarPorColuna(String filtro, String coluna) throws SQLException;
}
