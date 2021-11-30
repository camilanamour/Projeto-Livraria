package controller;

import java.sql.SQLException;

import entity.Edicao;
import entity.Editora;
import entity.Livro;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.EdicaoDao;
import persistence.IDaoEdicao;

public class EdicaoController {
	
	/** Classe para o controle da edição (cadastrar, pesquisar, alterar, deletar e listar).
	 * RF17-RN21: MANTER EDICAO
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
	public StringProperty edicao = new SimpleStringProperty();
	
	public StringProperty isbn = new SimpleStringProperty("");
    public ObjectProperty<Editora> editora = new SimpleObjectProperty<Editora>();
    public ObjectProperty<Livro> livro = new SimpleObjectProperty<Livro>();
    public IntegerProperty ano = new SimpleIntegerProperty();
    public IntegerProperty numeroPaginas = new SimpleIntegerProperty();
    public DoubleProperty valor = new SimpleDoubleProperty();
    public IntegerProperty quantidadeEstoque = new SimpleIntegerProperty();
    
    public StringProperty filtro = new SimpleStringProperty("");
    private ObjectProperty<String> coluna = new SimpleObjectProperty<>("Selecione");
    
    private ObservableList<Edicao> edicoes = FXCollections.observableArrayList();
    private ObservableList<Edicao> edicoesBusca = FXCollections.observableArrayList();
    private ObservableList<Livro> livros = FXCollections.observableArrayList();
    private ObservableList<Editora> editoras = FXCollections.observableArrayList();
    
    private IDaoEdicao edicaoDao;
    
    /**
     * Conecta com a persistencia da edição
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public EdicaoController() throws ClassNotFoundException, SQLException {
		edicaoDao = new EdicaoDao();
		listar();
		listarBusca();
	}
    
    /**
     * Inserir edição
     * @throws SQLException
     */
    public void adicionar() throws SQLException{
    	Edicao e = toEntity();
		edicaoDao.inserir(e, e.getEditora(), e.getLivro());
		listarBusca();
		listar();
    }
    
    /**
     * Pesquisar a edição
     * @throws SQLException
     */
    public void pesquisar() throws SQLException{
    	String codEdicao = (this.removeCaracteresEspeciais(edicao.get()));
    	Edicao e = edicaoDao.pesquisar(codEdicao);
		this.fromEntity(e);
    }
    
    /**
     * Alterar a edição
     * @throws SQLException
     */
    public void alterar() throws SQLException{
		Edicao e = toEntity();
		edicaoDao.alterar(e, e.getEditora(), e.getLivro());
		listarBusca();
		listar();
	}
	
    /**
     * Deletar a edição
     * @throws SQLException
     */
	public void deletar() throws SQLException{
		Edicao e = toEntity();
		edicaoDao.excluir(e);
		listarBusca();
		listar();
	}
	
	/**
	 * Limpar os campos
	 */
	public void limpar(){
		Edicao e = new Edicao();
		this.fromEntity(e);
		coluna.set("Selecione");
		
		filtro.set("");
		edicao.set("");
	}
	
	/**
	 * Listar para a tabela edição
	 * @throws SQLException
	 */
    private void listar() throws SQLException{
		edicoes.clear();
		edicoes.addAll(edicaoDao.listar());
	}
    
    /**
     * Listar todas as edições 
     * @throws SQLException
     */
    public void listarBusca() throws SQLException{
		edicoesBusca.clear();
		edicoesBusca.addAll(edicaoDao.listar());
	}
    
    /**
     * Listar pelo filtro e pela coluna
     * @throws SQLException
     */
    public void listarPorColuna() throws SQLException{
		edicoes.clear();
		edicoes.addAll(edicaoDao.listarPorColuna(filtro.get(), coluna.get()));
	}
    
    /**
     * @return lista de edições (filtro)
     */
    public ObservableList<Edicao> getListaEdicoes(){
		return edicoes;
	}
    
    public ObjectProperty<String> ColunaProperty(){
		return coluna;
	}

    /**
     * @return lista de edições (todas)
     */
    public ObservableList<Edicao> getListaBusca(){
		return edicoesBusca;
	}
    
    /**
     * @return lista de livros
     */
    public ObservableList<Livro> getListaLivro(){
    	return livros;
    }
    
    /**
     * @return lista de editoras
     */
    public ObservableList<Editora> getListaEditora(){
    	return editoras;
    }
    
    public Edicao toEntity() {
		Edicao e = new Edicao();
		if(isbn.get() != "0"){
        	e.setIsbn(removeCaracteresEspeciais(isbn.get()));
        }
		Editora d = new Editora();
		d = editora.get();
		d.setCnpj(removeCaracteresEspeciais(d.getCnpj()));
        e.setEditora(d);
        e.setLivro(livro.get());
        e.setAno(ano.get());
        e.setNumeroPaginas(numeroPaginas.get());
        e.setValorUnitario(valor.get());
        e.setQuantidadeEstoque(quantidadeEstoque.get());
        return e;
    }

    public void fromEntity(Edicao e) {
    	if(e.getIsbn() != "0"){
    		isbn.set(e.getIsbn());
    	}
    	editora.set(e.getEditora());
        livro.set(e.getLivro());
        ano.set(e.getAno());
        numeroPaginas.set(e.getNumeroPaginas());
        valor.set(e.getValorUnitario());
        quantidadeEstoque.set(e.getQuantidadeEstoque());
        
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
