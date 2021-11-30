package controller;

import java.sql.SQLException;
import java.util.List;

import entity.Autor;
import entity.Editora;
import entity.Livro;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.EditoraDao;
import persistence.IDaoEditora;

public class EditoraController {
	
	/** Classe para tratar os dados da editora (cadastrar, pesquisar, alterar, deletar e listar).
	 * RF12-RN16: MANTER EDITORA
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
	public ObjectProperty<Editora> editora = new SimpleObjectProperty<Editora>();
	
	public StringProperty cnpj = new SimpleStringProperty("");
    public StringProperty nome = new SimpleStringProperty("");
    public StringProperty email = new SimpleStringProperty("");
    public StringProperty telefone = new SimpleStringProperty("");
    public StringProperty logradouro = new SimpleStringProperty("");
    public IntegerProperty numero = new SimpleIntegerProperty();
    public StringProperty complemento = new SimpleStringProperty("");
    public StringProperty cep = new SimpleStringProperty("");
    private ObjectProperty<String> coluna = new SimpleObjectProperty<>("Selecione");
    public StringProperty filtro = new SimpleStringProperty("");
    
    private ObservableList<Editora> editoras = FXCollections.observableArrayList();
    private ObservableList<Editora> editorasBusca = FXCollections.observableArrayList();
    private IDaoEditora editoraDao;
    
    /**
     * Conectar persistencia com editora
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public EditoraController() throws ClassNotFoundException, SQLException {
		editoraDao = new EditoraDao();
		listar();
		listarBusca();
	}
    
    /**
     * Inserir Editora
     * @throws SQLException
     */
    public void adicionar() throws SQLException{
    	Editora e = toEntity();
		editoraDao.inserir(e);
		listarBusca();
		listar();
    }
    
    /**
     * Pesquisar Editora
     * @throws SQLException
     */
    public void pesquisar() throws SQLException{
    	Editora f = editora.get();
    	f.setCnpj(this.removeCaracteresEspeciais(f.getCnpj()));
    	Editora e = editoraDao.pesquisar(f);
		this.fromEntity(e);
    }
    
    /**
     * Alterar Editora
     * @throws SQLException
     */
    public void alterar() throws SQLException{
		Editora e = toEntity();
		editoraDao.alterar(e);
		listarBusca();
		listar();
	}
	
    /**
     * Deletar Editora
     * @throws SQLException
     */
	public void deletar() throws SQLException{
		Editora e = toEntity();
		editoraDao.excluir(e);
		listarBusca();
		listar();
	}
	
	/**
	 * Limpar Campos
	 */
	public void limpar(){
		Editora e = new Editora();
		this.fromEntity(e);
		coluna.set("Selecione");
		filtro.set("");
	}
	
	/**
	 * Listar todos editoras
	 * @throws SQLException
	 */
    private void listar() throws SQLException{
		editoras.clear();
		editoras.addAll(editoraDao.listar());
	}
    
    /**
     * Listar todas editoras (para buscar)
     * @throws SQLException
     */
    public void listarBusca() throws SQLException{
		editorasBusca.clear();
		editorasBusca.addAll(editoraDao.listar());
	}
    
    /**
     * Listar editoras (para filtro)
     * @throws SQLException
     */
    public void listarPorColuna() throws SQLException{
		editoras.clear();
		editoras.addAll(editoraDao.listarPorColuna(filtro.get(), coluna.get()));
	}
    
    /**
     * @return lista de editoras
     */
    public ObservableList<Editora> getListaEditoras(){
		return editoras;
	}
    
    public ObjectProperty<String> ColunaProperty(){
		return coluna;
	}
    
    /**
     * @return lista de editorar (para busca - todas)
     */
    public ObservableList<Editora> getListaBusca(){
		return editorasBusca;
	}
    
    /**
     * Gera Editora
     * @return Editora
     */
	public Editora toEntity() {
		Editora e = new Editora();
		if(cnpj.get() != "0"){
        	e.setCnpj(this.removeCaracteresEspeciais(cnpj.get()));
        }
        e.setNome(nome.get());
        e.setEmail(email.get());
        e.setTelefone(this.removeCaracteresEspeciais(telefone.get()));
        e.setLogradouro(logradouro.get());
        e.setNumero(numero.get());
        e.setComplemento(complemento.get());
        e.setCep(this.removeCaracteresEspeciais(cep.get()));
        return e;
    }

	/**
	 * Manda Editora para os campos
	 * @param e
	 */
    public void fromEntity(Editora e) {
    	if(e.getCnpj() != "0"){
            cnpj.set(e.getCnpj());
    	}
    	nome.set(e.getNome());
        email.set(e.getEmail());
        telefone.set(e.getTelefone());
        logradouro.set(e.getLogradouro());
        numero.set(e.getNumero());
        complemento.set(e.getComplemento());
        cep.set(e.getCep());
    }
    
    public String removeCaracteresEspeciais(String str){
    	String letras[] = str.split("");
    	String formatada = "";
    	for (String letra : letras){
			if(letra.equals(".")){
			} else if (letra.equals(",")){
			} else if (letra.equals("-")){
			} else if (letra.equals("/")){
			} else {
				formatada = formatada + letra;
			}
		}
    	return formatada;
    }
}
