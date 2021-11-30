package controller;

import java.sql.SQLException;

import entity.Autor;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.AutorDao;
import persistence.IDaoAutor;

public class AutorController {
	
	/** Classe para controlar o autor (cadastrar, pesquisar, alterar, deletar e listar).
	 * RF07-RF11: MANTER AUTOR
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */

	public ObjectProperty<Autor> autorBusca = new SimpleObjectProperty<Autor>();

	public IntegerProperty id = new SimpleIntegerProperty();
	public StringProperty nome = new SimpleStringProperty("");
	public StringProperty nascimento = new SimpleStringProperty("");
	public StringProperty pais = new SimpleStringProperty("");
	public StringProperty biografia = new SimpleStringProperty("");

	public StringProperty filtro = new SimpleStringProperty("");
	private ObjectProperty<String> coluna = new SimpleObjectProperty<>("Selecione");

	private ObservableList<Autor> autores = FXCollections.observableArrayList();
	private ObservableList<Autor> autoresBusca = FXCollections.observableArrayList();

	private IDaoAutor autDao;

	/**
	 * Conecta com a persistencia do autor
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public AutorController() throws ClassNotFoundException, SQLException {
		autDao = new AutorDao();
		listar();
		listarBusca();
	}

	/**
	 * Pesquisar autor para a associativa com o livro
	 * @param aut - autor
	 * @throws SQLException
	 */
	public void pesquisarParaLivro(Autor aut) throws SQLException {
		autDao.pesquisar(aut);
	}

	/**
	 * Inserir o autor
	 * @throws SQLException
	 */
	public void inserir() throws SQLException {
		Autor a = toEntity();
		
		autDao.inserir(a);
		listar();
		listarBusca();
	}

	/**
	 * Pesquisar autor
	 * @throws SQLException
	 */
	public void pesquisar() throws SQLException {
		Autor a = autDao.pesquisar(autorBusca.get());
		this.fromEntity(a);
	}

	/**
	 * Alterar autor
	 * @throws SQLException
	 */
	public void alterar() throws SQLException {
		Autor a = toEntity();
		autDao.alterar(a);
		listar();
		listarBusca();
	}

	/**
	 * Deletar autor
	 * @throws SQLException
	 */
	public void deletar() throws SQLException {
		Autor a = toEntity();
		autDao.excluir(a);
		listar();
		listarBusca();
	}

	/**
	 * Listar na lista principal
	 * @throws SQLException
	 */
	public void listar() throws SQLException {
		autores.clear();
		autores.addAll(autDao.listar());
	}

	/**
	 * Lista para a busca no combobox
	 * @throws SQLException
	 */
	public void listarBusca() throws SQLException {
		autoresBusca.clear();
		autoresBusca.addAll(autDao.listar());
	}
	
	/**
	 * Listar segundo o filtro e a coluna desejada
	 * @throws SQLException
	 */
	public void listarPorColuna() throws SQLException{
		autores.clear();
		autores.addAll(autDao.listarPorColuna(filtro.get(), coluna.get()));
		filtro.set("");
	}
	
	/**
	 * Limpar os campos dos dados
	 * @throws SQLException
	 */
	public void limpar() throws SQLException{
		Autor a = new Autor();
		this.fromEntity(a);
		filtro.set("");
		coluna.set("Selecione");
		id.set(0);
	}

	/**
	 * @return lista de autores
	 */
	public ObservableList<Autor> getLista() {
		return autores;
	}

	/**
	 * @return lista de todos os autores
	 */
	public ObservableList<Autor> getListaBusca() {
		return autoresBusca;
	}

	/**
	 * @return lista de colunas do autor
	 */
	public ObjectProperty<String> ColunaProperty() {
		return coluna;
	}
	
	/**
	 * Conversor para a data brasileira
	 * @param data 
	 * @return String - data convertida
	 */
	public String converterDataBR (String data){
		if(data != null){
			String[] dataBR = data.split("-");
			return dataBR[2]+"/"+dataBR[1]+"/"+dataBR[0];
		}
		return null;
	}
	
	/**
	 * Conversor para a data americana
	 * @param data 
	 * @return String - data convertida
	 */
	public String converterDataEUA (String data){
		if(data != null){
			String[] dataEUA = data.split("/");
			return dataEUA[2]+"-"+dataEUA[1]+"-"+dataEUA[0];
		}
		return null;
	}
	
	/**
	 * Gera o autor
	 * @return Autor
	 */
	public Autor toEntity() {
        Autor a = new Autor();
        if(id.get() != 0){
        	a.setCodigo(id.get());
        }
        a.setNome(nome.get());
        a.setDataNascimento(this.converterDataEUA(nascimento.get()));
        a.setPais(pais.get());
        a.setBiografia(biografia.get());
        return a;
    }

	/**
	 * Manda o autor para os campos
	 * @param a - autor
	 * @throws SQLException
	 */
    public void fromEntity(Autor a) throws SQLException {
    	if(a != null){
    		if(a.getCodigo() != 0){
    			id.set(a.getCodigo());
    		}
        	nome.set(a.getNome());
        	nascimento.set(this.converterDataBR(a.getDataNascimento()));
        	pais.set(a.getPais());
        	biografia.set(a.getBiografia());
    	}
    }

}
