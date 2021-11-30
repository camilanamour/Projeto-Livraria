package boundary;

import java.sql.SQLException;

import controller.EdicaoController;
import controller.EditoraController;
import controller.LivroController;
import entity.Edicao;
import entity.Editora;
import entity.Livro;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.converter.NumberStringConverter;

public class EdicaoBoundary {
	
	/** Classe para a tela das edições.
	 * RNF01: USABILIDADE - interface gráfica simples
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
	GridPane panEdicao;
	
	ComboBox<Livro> comboBoxLivro;
	ComboBox<Editora> comboBoxEditora;
	ComboBox<String> comboBoxFiltro;
	
	Button btnPesquisar;
	Button btnLimpar;
	Button btnInserir;
	Button btnAlterar;
	Button btnExcluir;
	Button btnListar;
	
	TextField txtISBN;
	TextField txtAno;
	TextField txtNumeroPaginas;
	TextField txtValorUnitario;
	TextField txtQuantidadeEstoque;
	TextField txtFiltro;
	TextField txtBuscar;

	
	TableView<Edicao> table;
	
	EdicaoController control;
	LivroController controlLivro;
	EditoraController controlEditora;
	
	public EdicaoBoundary(){
		txtBuscar = new TextField();
		comboBoxLivro = new ComboBox<Livro>();
		comboBoxEditora = new ComboBox<Editora>();
		comboBoxFiltro = new ComboBox<String>();
		
		btnPesquisar = new Button("Pesquisar");
		btnLimpar = new Button("Limpar");
		btnInserir = new Button("Inserir");
		btnAlterar = new Button("Alterar");
		btnExcluir = new Button("Excluir");
		btnListar = new Button("Listar");
		
		txtISBN = new TextField();
		txtAno = new TextField();
		txtNumeroPaginas = new TextField();
		txtValorUnitario = new TextField();
		txtQuantidadeEstoque = new TextField();
		txtFiltro = new TextField();
		
		try {
			control = new EdicaoController();
			controlLivro = new LivroController();
			controlEditora = new EditoraController();
		} catch (ClassNotFoundException | SQLException e2) {
			new Alert(Alert.AlertType.ERROR, e2.getMessage()).showAndWait();
		}		
		
	}
	/**
	 * Método para montar a tela de edição
	 * @return panEdicao - painel com todos os componentes da edição inseridos
	 */
	
	public GridPane montarTelaEdicao(){
		
		panEdicao = new GridPane();
		BorderPane.setMargin(panEdicao, new Insets(10, 10, 10, 10));
		panEdicao.setHgap(15);
		panEdicao.setVgap(20);
		panEdicao.setPrefSize(1000, 800);
		
		Bindings.bindBidirectional(txtISBN.textProperty(), control.isbn);
		comboBoxLivro.valueProperty().bindBidirectional(control.livro);
		comboBoxEditora.valueProperty().bindBidirectional(control.editora);
	    Bindings.bindBidirectional(txtAno.textProperty(), control.ano, new NumberStringConverter());
	    Bindings.bindBidirectional(txtNumeroPaginas.textProperty(), control.numeroPaginas, new NumberStringConverter());
	    Bindings.bindBidirectional(txtValorUnitario.textProperty(), control.valor, new NumberStringConverter());
	    Bindings.bindBidirectional(txtQuantidadeEstoque.textProperty(), control.quantidadeEstoque, new NumberStringConverter());
	    Bindings.bindBidirectional(txtFiltro.textProperty(), control.filtro);
	    
	    Bindings.bindBidirectional(txtBuscar.textProperty(), control.edicao);
		comboBoxFiltro.valueProperty().bindBidirectional(control.ColunaProperty());
		
		//Área Busca
		
		panEdicao.add(new Label("Buscar: "), 0, 1);
		
		Pane buscaPane = new Pane();
		
		txtBuscar.setPrefWidth(400);
		buscaPane.getChildren().addAll(txtBuscar);
		
		btnPesquisar.relocate(410, 0);
		btnLimpar.relocate(500, 0);
		
		buscaPane.getChildren().addAll(btnPesquisar, btnLimpar);
		panEdicao.add(buscaPane, 1, 1);
		
		btnPesquisar.setOnAction((e) ->{
			try {
				control.pesquisar();
				btnInserir.setDisable(true);
				btnAlterar.setDisable(false);
				btnExcluir.setDisable(false);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnLimpar.setOnAction((e) ->{
				control.limpar();
				comboBoxLivro.setItems(controlLivro.getLista());
				comboBoxLivro.getSelectionModel().selectFirst();
				comboBoxEditora.setItems(controlEditora.getListaEditoras());
				comboBoxEditora.getSelectionModel().selectFirst();
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
				btnExcluir.setDisable(true);
		});
		
		//Área IAE (Inserir, Alterar, Exluir
		
		panEdicao.add(new Label("ISBN: "), 0, 2);
		panEdicao.add(new Label("Livro: "), 0, 3);
		panEdicao.add(new Label("Editora: "), 0, 4);
		panEdicao.add(new Label("Ano: "), 0, 5);
		panEdicao.add(new Label("Número de páginas: "), 2, 2);
		panEdicao.add(new Label("Valor Unitário: "), 2, 3);
		panEdicao.add(new Label("Quantidade em estoque: "), 2, 4);
		

		panEdicao.add(txtISBN, 1, 2);
		
		comboBoxLivro.setItems(controlLivro.getLista());
		comboBoxLivro.getSelectionModel().select(0);
		comboBoxLivro.setPrefWidth(450);
		panEdicao.add(comboBoxLivro, 1, 3);
		
		comboBoxEditora.setItems(controlEditora.getListaEditoras());
		comboBoxEditora.getSelectionModel().select(0);
		comboBoxEditora.setPrefWidth(450);
		panEdicao.add(comboBoxEditora, 1, 4);
		

		panEdicao.add(txtAno, 1, 5);

		panEdicao.add(txtNumeroPaginas, 3, 2);

		panEdicao.add(txtValorUnitario, 3, 3);

		panEdicao.add(txtQuantidadeEstoque, 3, 4);
		
		Pane iaePane = new Pane();
		
		btnAlterar.relocate(70, 0);
		btnExcluir.relocate(145, 0);
		
		btnInserir.setDisable(false);
		btnAlterar.setDisable(true);
		btnExcluir.setDisable(true);
		
		iaePane.getChildren().addAll(btnInserir, btnAlterar, btnExcluir);
		panEdicao.add(iaePane, 3, 6);
		
		btnInserir.setOnAction((e) ->{
			try {
				control.adicionar();
				new Alert(Alert.AlertType.INFORMATION, "Edicao inserida com sucesso!").showAndWait();
				this.atualizarTela();
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
				btnExcluir.setDisable(true);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnAlterar.setOnAction((e) ->{
			try {
				control.alterar();
				new Alert(Alert.AlertType.INFORMATION, "Edicao alterada com sucesso!").showAndWait();
				this.atualizarTela();
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
				btnExcluir.setDisable(true);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnExcluir.setOnAction((e) ->{
			try {
				control.deletar();
				new Alert(Alert.AlertType.INFORMATION, "Edicao deletada com sucesso!").showAndWait();
				this.atualizarTela();
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
				btnExcluir.setDisable(true);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		//Area da Tabela
		
		Pane panTabela = new Pane();
		
		Label lblPor = new Label("por");
		lblPor.relocate(250, 0);
		panTabela.getChildren().addAll(new Label("Filtrar"), lblPor);
		
		txtFiltro.prefWidth(400);
		txtFiltro.relocate(50, 0);
		panTabela.getChildren().add(txtFiltro);
		
		comboBoxFiltro.getItems().clear();
		comboBoxFiltro.getItems().addAll("isbn", "livro", "editora", "ano", "numero_paginas", "valor_unitario", "qtd_estoque");
		comboBoxFiltro.setPrefWidth(400);
		comboBoxFiltro.relocate(290, 0);
		panTabela.getChildren().add(comboBoxFiltro);
		
		btnListar.relocate(710, 0);
		panTabela.getChildren().add(btnListar);
		btnListar.setOnAction((e) ->{
			try {
				control.listarPorColuna();
				table.setItems(control.getListaEdicoes());
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		panEdicao.add(panTabela, 0, 7, 8, 7);
		this.criarTabela();
		
		//ColumnConstraints e Retorno
		
		ColumnConstraints colConstraint = new ColumnConstraints();
        colConstraint.setFillWidth(true);
        colConstraint.setHgrow(Priority.ALWAYS);

        panEdicao.getColumnConstraints().addAll(new ColumnConstraints(), colConstraint, new ColumnConstraints(), colConstraint, new ColumnConstraints());
		
		return panEdicao;
	}
	
	private void atualizarTela() throws SQLException{
		control.limpar();
		comboBoxLivro.setItems(controlLivro.getLista());
		comboBoxLivro.getSelectionModel().selectFirst();
		comboBoxEditora.setItems(controlEditora.getListaEditoras());
		comboBoxEditora.getSelectionModel().selectFirst();
	}
	
	private void criarTabela(){
		table = new TableView<Edicao>();
		
		table.setMaxSize(2000, 2000);
		panEdicao.add(table, 0, 9, 8, 9);
		
		TableColumn<Edicao, String> col1 = new TableColumn<>("ISBN");
        col1.setCellValueFactory(
                new PropertyValueFactory<Edicao, String>("isbn")
        );
        
		TableColumn<Edicao, Livro> col2 = new TableColumn<>("Livro");
        col2.setCellValueFactory(
                new PropertyValueFactory<Edicao, Livro>("Livro")
        );
        
        TableColumn<Edicao, Editora> col3 = new TableColumn<>("Editora");
        col3.setCellValueFactory(
                new PropertyValueFactory<Edicao, Editora>("Editora")
        );
        
        TableColumn<Edicao, Integer> col4 = new TableColumn<>("Ano");
        col4.setCellValueFactory(
                new PropertyValueFactory<Edicao, Integer>("ano")
        );
        
        TableColumn<Edicao, Integer> col5 = new TableColumn<>("Numero de Paginas");
        col5.setCellValueFactory(
                new PropertyValueFactory<Edicao, Integer>("numeroPaginas")
        );
        
        TableColumn<Edicao, Double> col6 = new TableColumn<>("Valor Unitario");
        col6.setCellValueFactory(
                new PropertyValueFactory<Edicao, Double>("valorUnitario")
        );
        
        TableColumn<Edicao, Integer> col7 = new TableColumn<>("Quantidade em Estoque");
        col7.setCellValueFactory(
                new PropertyValueFactory<Edicao, Integer>("quantidadeEstoque")
        );
        
        table.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
				control.fromEntity(novo);
				btnInserir.setDisable(true);
				btnAlterar.setDisable(false);
				btnExcluir.setDisable(false);
        });
        
        table.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
        table.setItems(control.getListaEdicoes());
	}
}
