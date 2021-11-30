package boundary;

import java.sql.SQLException;

import controller.AutorController;
import controller.CategoriaController;
import controller.LivroController;
import entity.Autor;
import entity.Categoria;
import entity.Livro;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;

public class LivroBoundary{
	
	/** Classe para a tela dos livros.
	 * RNF01: USABILIDADE - interface gráfica simples
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
	private ComboBox<Livro> cbBusca;    
	private ComboBox<Categoria> cbCategoria;
	private ComboBox<Autor> cbAutor;
	private ComboBox<String> cbFormato;
	private ComboBox<String> cbColuna;
	
	private TextField txtcodigo;  private TextField txtTitulo;  
	private TextField txtAno;     private TextField txtLingua;
	private TextField txtFiltrar; private TextArea txtResumo;
	
	private Button btnPesquisar;  private Button btnLimpar;  
	private Button btnAutor;      private Button btnCategoria;
	private Button btnInserir;    private Button btnAlterar;
	private Button btnDeletar;    private Button btnListar;
	
	private TableView<Autor> tabAutores = new TableView<Autor>();
	private TableView<Livro> tabLivros = new TableView<Livro>();
	
	private LivroController control;
	private CategoriaController controlCat;
	private AutorController controlAut;
	
	public LivroBoundary() {
		
		cbBusca = new ComboBox<Livro>(); cbBusca.setPrefWidth(400);
		btnPesquisar = new Button("Pesquisar");
		btnLimpar = new Button("Limpar");
		
		txtcodigo = new TextField();
		txtcodigo.setEditable(false);
		txtTitulo = new TextField(); txtTitulo.setPrefWidth(300);
		txtAno = new TextField();
		txtLingua = new TextField();
		txtResumo = new TextArea();
		txtResumo.setPrefWidth(600); txtResumo.setPrefHeight(100);
		
		cbCategoria = new ComboBox<Categoria>(); cbCategoria.setPrefWidth(200);
		cbAutor = new ComboBox<Autor>(); cbAutor.setPrefWidth(250);
		
		cbFormato = new ComboBox<String>(); cbFormato.setPrefWidth(200);
		cbFormato.getItems().addAll("Brochura", "Capa dura");

		btnAutor = new Button("Adicionar Autor");
		btnCategoria = new Button("Nova Categoria");
		
		btnInserir = new Button("Inserir");
		btnAlterar = new Button("Alterar");
		btnDeletar = new Button("Deletar");
		
		txtFiltrar = new TextField();
		cbColuna = new ComboBox<String>(); cbColuna.setPrefWidth(180);
		btnListar = new Button("Listar");
		
		try {
			control = new LivroController();
			controlCat = new CategoriaController();
			controlAut = new AutorController();
		} catch (ClassNotFoundException | SQLException e1) {
			new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
		}
		
		this.criarTabelaAutores();
		this.criarTabelaLivros();
	}
	
	/**
	 * Método para a montagem da tela dos dados para o livro
	 * @return panLivro - painel com os componentes de tela livro já inseridos
	 */
	public BorderPane montarTelaLivro(){
		BorderPane panLivro = new BorderPane();
		BorderPane.setMargin(panLivro, new Insets(20, 10, 0, 10));
		
		cbBusca.valueProperty().bindBidirectional(control.livro);
		
		Bindings.bindBidirectional(txtcodigo.textProperty(), control.id, new NumberStringConverter());
	    Bindings.bindBidirectional(txtTitulo.textProperty(), control.titulo);
	    Bindings.bindBidirectional(txtAno.textProperty(), control.ano, new NumberStringConverter());
	    Bindings.bindBidirectional(txtLingua.textProperty(), control.lingua);
	    cbFormato.valueProperty().bindBidirectional(control.formatoProperty());
	    Bindings.bindBidirectional(txtResumo.textProperty(), control.resumo);
	    cbCategoria.valueProperty().bindBidirectional(control.categoria);
	    cbAutor.valueProperty().bindBidirectional(control.autor);
	    
	    Bindings.bindBidirectional(txtFiltrar.textProperty(), control.filtro);
	    cbColuna.valueProperty().bindBidirectional(control.ColunaProperty());
	    
	    try {
			control.limpar();
		} catch (SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
	    
//		Painel os componentes de Busca ---------------------------------
		GridPane panBusca = new GridPane();
		panBusca.setHgap(20);
		panBusca.add(new Label("Buscar"), 0, 0);
		panBusca.add(cbBusca, 1, 0);
		cbBusca.setItems(control.getListaBusca());
		cbBusca.getSelectionModel().select(0);
		panBusca.add(btnPesquisar, 2, 0);
		panBusca.add(btnLimpar, 3, 0);
		
		btnInserir.setDisable(false);
		btnAlterar.setDisable(true);
		btnDeletar.setDisable(true);
		
		btnPesquisar.setOnAction((e) ->{
			try {
				control.pesquisar();
				tabAutores.setItems(control.getListaAutores());
				btnInserir.setDisable(true);
				btnAlterar.setDisable(false);
				btnDeletar.setDisable(false);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnLimpar.setOnAction((e) ->{
			try {
				control.limpar();
				cbCategoria.setItems(controlCat.getLista());
				cbCategoria.getSelectionModel().selectFirst();
				cbBusca.getSelectionModel().selectFirst();
				control.listar();
				tabLivros.setItems(control.getListaBusca());
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
				btnDeletar.setDisable(true);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
//		Painel para os campos do Livro ---------------------------------
		BorderPane panCentro = new BorderPane();
		BorderPane.setMargin(panCentro, new Insets(20, 10, 10, 0));
		
//		Campos dos dados ---- (Esquerdo + top) -----------------
		BorderPane panEsquerda = new BorderPane();
		GridPane panCampos = new GridPane();
		panCampos.add(new Label("Código"), 0, 0);
		panCampos.add(txtcodigo, 1, 0);
		panCampos.add(new Label("Ano"), 2, 0);
		panCampos.add(txtAno, 3, 0);
			
		panCampos.add(new Label("Título"), 0, 1);
		panCampos.add(txtTitulo, 1, 1);
		panCampos.add(new Label("Categoria"), 2, 1);
		cbCategoria.setItems(controlCat.getLista());
		cbCategoria.getSelectionModel().selectFirst();
		panCampos.add(cbCategoria, 3, 1);
		panCampos.add(btnCategoria, 4, 1);
		
		panCampos.add(new Label("Língua"), 0, 2);
		panCampos.add(txtLingua, 1, 2);
		panCampos.add(new Label("Formato"), 2, 2);
		panCampos.add(cbFormato, 3, 2);
		panCampos.setHgap(20); panCampos.setVgap(20);
		
//		Campo resumo (top) + Buttons (bottom) -- (Esquerdo + bottom) --
		BorderPane panAux = new BorderPane();
		GridPane panResumo = new GridPane();
		BorderPane.setMargin(panResumo, new Insets(20, 10, 10, 0));
		panResumo.add(new Label("Resumo"), 0, 0);
		panResumo.add(txtResumo, 1, 0);	
		panResumo.setHgap(15);
		
		GridPane panManipulacao = new GridPane();
		BorderPane.setMargin(panManipulacao, new Insets(0, 0, 10, 50));
		panManipulacao.add(btnInserir, 1, 0);
		panManipulacao.add(btnAlterar, 2, 0);	
		panManipulacao.add(btnDeletar, 3, 0);
		panManipulacao.setHgap(15);
		
		panAux.setTop(panResumo);
		panAux.setCenter(panManipulacao);
//		------------------------------------------------------------
		
//		Lista de Autores do Livro -------- (direto) ----------------
		GridPane panAutor = new GridPane();
		BorderPane.setMargin(panAutor, new Insets(0, 10, 10, 20));
		panAutor.add(new Label("Escolha os Autores do livro"), 0, 0);
		panAutor.add(tabAutores, 0, 1);	
		this.criarTabelaAutores();
		panAutor.add(cbAutor, 0, 2);
		cbAutor.setItems(controlAut.getLista());
		cbAutor.getSelectionModel().select(0);
		panAutor.add(btnAutor, 1, 2);
		panAutor.setVgap(10); panAutor.setHgap(10);
//		------------------------------------------------------------
		
		panEsquerda.setTop(panCampos); // Top
		panEsquerda.setCenter(panAux); // Bottom
		
		panCentro.setLeft(panEsquerda);// Esquerda
		panCentro.setCenter(panAutor); // Direita
		
		btnInserir.setOnAction((e) ->{
			try {
				control.inserir();
				new Alert(Alert.AlertType.INFORMATION, "Livro inserido com sucesso!").showAndWait();
				this.atualizarTela();
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
				btnDeletar.setDisable(true);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});

		btnAlterar.setOnAction((e) ->{
			try {
				control.alterar();
				new Alert(Alert.AlertType.INFORMATION, "Livro alterado com sucesso!").showAndWait();
				this.atualizarTela();
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
				btnDeletar.setDisable(true);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnDeletar.setOnAction((e) ->{
			try {
				control.deletar();
				new Alert(Alert.AlertType.INFORMATION, "Livro deletado com sucesso!").showAndWait();
				this.atualizarTela();
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
				btnDeletar.setDisable(true);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnAutor.setOnAction((e) ->{
			try {
				control.adicionarAutor();
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnCategoria.setOnAction((e) ->{
//			Nova Categoria
			TextInputDialog tidCategoria = new TextInputDialog();
			tidCategoria.setHeaderText("Qual é o nome da nova categoria?");
			tidCategoria.showAndWait();
			try {
				String msg = controlCat.adicionar(tidCategoria.getEditor().getText());
				new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
				cbCategoria.setItems(controlCat.getLista());
				cbCategoria.getSelectionModel().selectLast();
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
//		Painel para a tabela e filtros ---------------------------------
		BorderPane panTabela = new BorderPane();
		GridPane panFiltro = new GridPane();
		BorderPane.setMargin(panFiltro, new Insets(10, 10, 10, 0));
		panFiltro.add(new Label("Filtro"), 0, 0);
		panFiltro.add(txtFiltrar, 1, 0);
		panFiltro.add(new Label("por"), 2, 0);
		panFiltro.add(cbColuna, 3, 0);
		cbColuna.getItems().clear();
		cbColuna.getItems().addAll("titulo", "ano", "lingua", "formato", "categoria", "autor");
		panFiltro.add(btnListar, 4, 0);
		panFiltro.setHgap(10);
		
		panTabela.setTop(panFiltro);
		panTabela.setCenter(tabLivros);
		this.criarTabelaLivros();
		
		btnListar.setOnAction((e) ->{
			try {
				control.listarPorColuna();
				tabLivros.setItems(control.getLista());
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
//		-----------------------------------------------------------------		
		
		panLivro.setTop(panBusca);
		panLivro.setBottom(panTabela);
		panLivro.setCenter(panCentro);
		
		return panLivro;
	}
	
	/**
	 * Ao clicar em atualizar a tela - limpa os campos e retorna a primeira posição dos comboboxs
	 */
	private void atualizarTela() throws SQLException{
		control.limpar();
		cbBusca.setItems(control.getListaBusca());
		cbBusca.getSelectionModel().selectFirst();
		cbCategoria.setItems(controlCat.getLista());
		cbCategoria.getSelectionModel().selectFirst();		
	}
	
	/**
	 * Criar a tabela principal dos Livros
	 */
	private void criarTabelaLivros(){
		tabLivros = new TableView<Livro>();
		tabLivros.setMaxSize(2000, 2000);
		
		TableColumn<Livro, String> col2 = new TableColumn<>("Título");
        col2.setCellValueFactory(
                new PropertyValueFactory<Livro, String>("titulo")
        );
        TableColumn<Livro, Integer> col3 = new TableColumn<>("Ano");
        col3.setCellValueFactory(
                new PropertyValueFactory<Livro, Integer>("ano")
        );
        TableColumn<Livro, String> col4 = new TableColumn<>("Língua");
        col4.setCellValueFactory(
                new PropertyValueFactory<Livro, String>("lingua")
        );
        TableColumn<Livro, String> col5 = new TableColumn<>("Formato");
        col5.setCellValueFactory(
                new PropertyValueFactory<Livro, String>("formato")
        );
        
        TableColumn<Livro, String> col6 = new TableColumn<>("Categoria");
        col6.setCellValueFactory((item) -> {
        	String categoria = item.getValue().getCategoria().getNome();
        	return new ReadOnlyStringWrapper(categoria); 
        });
        
        TableColumn<Livro, String> col7 = new TableColumn<>("Autores");
        col7.setCellValueFactory((item) -> {
        	String autores = "";
        	int num = item.getValue().getAutores().size();
        	for(int i=0; i<num; i++){
        		if(i == 0){
        			autores = item.getValue().getAutores().get(i).getNome();
        		} else {
        			autores += ", " + item.getValue().getAutores().get(i).getNome();
        		}
        	}
        	return new ReadOnlyStringWrapper(autores); 
        });
        
        tabLivros.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
        	try {
				control.fromEntity(novo);
				int codigo = control.id.get();
				control.listaAutorAtualizar(codigo);
				tabAutores.setItems(control.getListaAutores());
				btnInserir.setDisable(true);
				btnAlterar.setDisable(false);
				btnDeletar.setDisable(false);
			} catch (SQLException e) {
				new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
			}
        });

        tabLivros.getColumns().addAll(col2, col3, col4, col5, col6, col7);
        tabLivros.setItems(control.getLista());
	}
	
	/**
	 * Cria a tabela auxiliar para a associação de autor com livro
	 */
	private void criarTabelaAutores(){
		tabAutores = new TableView<Autor>();
		
		TableColumn<Autor, Integer> col1 = new TableColumn<>("Código");
        col1.setCellValueFactory(
                new PropertyValueFactory<Autor, Integer>("codigo")
        );
        
        TableColumn<Autor, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory(
                new PropertyValueFactory<Autor, String>("nome")
        );
        
        TableColumn<Autor, String> col3 = new TableColumn<>("");
        col3.setCellFactory( (tbcol) -> {
            Button btnRemover = new Button("Remover");
            TableCell<Autor, String> tcell = new TableCell<Autor, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btnRemover.setOnAction( (e) -> {
                            Autor aut = getTableView().getItems().get(getIndex());
                            control.removerAutor(aut);
                        });
                        setGraphic(btnRemover);
                        setText(null);
                    }
                }
            };
            return tcell;
            }
        );

        tabAutores.getColumns().addAll(col1,col2,col3);
        tabAutores.setItems(control.getListaAutores());
	}
}
