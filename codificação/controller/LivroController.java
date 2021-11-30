package controller;

import java.sql.SQLException;

import entity.Autor;
import entity.Categoria;
import entity.Livro;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.IDaoLivro;
import persistence.LivroDao;

public class LivroController {
	
	/** Classe para o controle dos dados do livro (cadastrar, pesquisar, alterar, deletar e listar).
	 * RF01-RN05: MANTER LIVRO
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
	public ObjectProperty<Livro> livro = new SimpleObjectProperty<Livro>();
	
	public IntegerProperty id = new SimpleIntegerProperty();
    public StringProperty titulo = new SimpleStringProperty("");
    public IntegerProperty ano = new SimpleIntegerProperty();
    public StringProperty lingua = new SimpleStringProperty("");
    private ObjectProperty<String> formato = new SimpleObjectProperty<>("Selecione");
    public StringProperty resumo = new SimpleStringProperty("");
    public ObjectProperty<Categoria> categoria = new SimpleObjectProperty<Categoria>();
    public ObjectProperty<Autor> autor = new SimpleObjectProperty<Autor>();
    
    public StringProperty filtro = new SimpleStringProperty("");
    private ObjectProperty<String> coluna = new SimpleObjectProperty<>("Selecione");
    
	private ObservableList<Autor> listaAutores = FXCollections.observableArrayList();
	private ObservableList<Livro> listaLivros = FXCollections.observableArrayList();
	private ObservableList<Livro> listaLivrosBusca = FXCollections.observableArrayList();
        
    private IDaoLivro livroDao;
    
    private AutorController controlAut = new AutorController();
    
    /**
     * Conectar persistencia do livro
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public LivroController() throws ClassNotFoundException, SQLException {
    	livroDao = new LivroDao();
    	listarBusca();
    	listar();
	}
	
    /**
     * Inserir livro
     * @throws SQLException
     */
	public void inserir() throws SQLException{
		Livro livro = toEntity();
		livro.setCodigo(livroDao.inserir(livro));
		for(Autor a : listaAutores){
			livroDao.inserirAssociativa(a.getCodigo(), livro.getCodigo());
		}
		listarBusca();
		listar();
	}
	
	/**
	 * Pesquisar Livro
	 * @throws SQLException
	 */
	public void pesquisar() throws SQLException{
		Livro l = livroDao.pesquisar(livro.get());
		this.fromEntity(l);
		listaAutores.clear();
		listaAutores.addAll(l.getAutores());
	}
	
	/**
	 * Alterar Livro
	 * @throws SQLException
	 */
	public void alterar() throws SQLException{
		Livro l = toEntity();
		livroDao.alterar(l);
		listarBusca();
		listar();
	}
	
	/**
	 * Deletar Livro
	 * @throws SQLException
	 */
	public void deletar() throws SQLException{
		Livro l = toEntity();
		livroDao.excluir(l);
		listarBusca();
		listar();
	}
	
	/**
	 * Limpar os campos
	 * @throws SQLException
	 */
	public void limpar() throws SQLException{
		Livro l = new Livro();
		this.fromEntity(l);
		listaAutores.removeAll(listaAutores);
		formato.set("Selecione");
		coluna.set("Selecione");
		filtro.set("");
		id.set(0);
	}
	
	/**
	 * Listar os livros
	 *@throws SQLException
	 */
	public void listar() throws SQLException{
		listaLivros.clear();
		listaLivros.addAll(livroDao.listar());
	}
	
	/**
	 * Listar para a busca de livros
	 * @throws SQLException
	 */
	public void listarBusca() throws SQLException{
		listaLivrosBusca.clear();
		listaLivrosBusca.addAll(livroDao.listar());
	}
	
	/**
	 * Listar os autores do livro pela classe associativa
	 * @param codigo do livro
	 * @throws SQLException
	 */
	public void listaAutorAtualizar(int codigo) throws SQLException{
		listaAutores.clear();
		listaAutores.addAll(livroDao.listarAutores(codigo));
	}
	
	/**
	 * Listar os livros pelo filtro e pela coluna
	 * @throws SQLException
	 */
	public void listarPorColuna() throws SQLException{
		listaLivros.clear();
		listaLivros.addAll(livroDao.listarPorColuna(filtro.get(), coluna.get()));
	}
	
	/**
	 * Adicionar o autor na lista associativa entre livro e autor
	 * @throws SQLException
	 */
	public void adicionarAutor() throws SQLException{
		Autor aut = new Autor();
		aut.setCodigo(autor.get().getCodigo());
		controlAut.pesquisarParaLivro(aut);
		listaAutores.add(aut);
	}
	
	/**
	 * Remover o autor na lista associativa entre livro e autor
	 * @throws SQLException
	 */
	public void removerAutor(Autor autor){
		listaAutores.remove(autor);
	}
	
	
	public ObjectProperty<String> formatoProperty(){
		return formato;
	}
	
	public ObjectProperty<String> ColunaProperty(){
		return coluna;
	}
	
	/**
	 * @return lista de autores da associativa
	 */
	public ObservableList<Autor> getListaAutores(){
		return listaAutores;
	}
	
	/**
	 * @return lista de livros
	 */
	public ObservableList<Livro> getLista(){
		return listaLivros;
	}
	
	/**
	 * @return lista de livros (para busca - todos)
	 */
	public ObservableList<Livro> getListaBusca(){
		return listaLivrosBusca;
	}
	
	/**
	 * Gera Livro
	 * @return Livro
	 */
	public Livro toEntity() {
        Livro l = new Livro();
        if(id.get() != 0){
        	l.setCodigo(id.get());
        }
        l.setTitulo(titulo.get());
        l.setAno(ano.get());
        l.setLingua(lingua.get());
        l.setFormato(formato.get());
        l.setResumo(resumo.get());
        l.setCategoria(categoria.get());
        l.setAutores(listaAutores);
        return l;
    }

	/**
	 * Manda o livro para os campos
	 * @param l - livro
	 * @throws SQLException
	 */
    public void fromEntity(Livro l) throws SQLException {
    	if(l.getCodigo() != 0){
            id.set(l.getCodigo());
    	}
        titulo.set(l.getTitulo());
        ano.set(l.getAno());
        lingua.set(l.getLingua());
        formato.set(l.getFormato());
        resumo.set(l.getResumo());
        categoria.set(l.getCategoria());
        listaAutores.addAll(livroDao.listarAutores(l.getCodigo()));
    }

}
