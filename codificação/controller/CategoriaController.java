package controller;

import java.sql.SQLException;

import entity.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.CategoriaDao;

public class CategoriaController {
	
	/** Classe para controlar a categoria (cadastrar, pesquisar e listar).
	 * RF06: CADASTRAR CATEGORIA
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
	private ObservableList<Categoria> categorias = FXCollections.observableArrayList();
	
	CategoriaDao catDao;
	
	/**
	 * Conectar com a persistencia da categoria
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public CategoriaController() throws ClassNotFoundException, SQLException {
		catDao = new CategoriaDao();
		listar();
	}
	
	/**
	 * Inserir categoria
	 * @param categoria
	 * @return String - mensagem para o usuário
	 * @throws SQLException
	 */
	public String adicionar(String categoria) throws SQLException{
		if(!categoria.equals("")){
			catDao.adicionar(categoria);
			listar();
			return "Nova categoria adicionada com sucesso!";
		} else {
			return "Campo Nulo: Não foi possível adicionar a categoria.";
		}
	}
	
	/**
	 * Pesquisar Categoria
	 * @param codigo da categoria
	 * @return Categoria
	 * @throws SQLException
	 */
	public Categoria buscar(int codigo) throws SQLException{
		return catDao.buscar(codigo);
	}
	
	/**
	 * Listar as categorias e colocar na lista
	 * @throws SQLException
	 */
	private void listar() throws SQLException{
		categorias.clear();
		categorias.addAll(catDao.listar());
	}
	
	/**
	 * @return lista de categorias
	 */
	public ObservableList<Categoria> getLista(){
		return categorias;
    }

}
