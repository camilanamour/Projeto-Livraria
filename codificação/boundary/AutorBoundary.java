package boundary;

import java.sql.SQLException;

import controller.AutorController;
import entity.Autor;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.converter.NumberStringConverter;

public class AutorBoundary {
	
	/** Classe para a tela dos autores.
	 * RNF01: USABILIDADE - interface gráfica simples
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
	private ComboBox<Autor> comboBoxBuscar = new ComboBox<Autor>();
	
	private Button btnInserir;
	private Button btnAlterar;
    private Button btnExcluir;
    
	private TableView<Autor> table = new TableView<Autor>();
	
	private AutorController control;
	
	public AutorBoundary() {
		try {
			control = new AutorController();
			this.atualizarTela();
		} catch (ClassNotFoundException | SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
		this.criarTabela();
	}
	
	/**
	 * Método para a montagem da tela dos dados para o autor
	 * @return panAutor - painel com os componentes de tela autor já inseridos
	 */
	
	public GridPane montarTelaAutor(){
		
		try {
			this.atualizarTela();
		} catch (SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}

		GridPane panAutor = new GridPane();
		BorderPane.setMargin(panAutor, new Insets(10, 10, 10, 10));
		panAutor.setHgap(15);
		panAutor.setVgap(20);
		panAutor.setPrefSize(1000, 800);
		
		//Área Busca
		
		panAutor.add(new Label("Buscar: "), 0, 1);
		
		Pane buscaPane = new Pane();
		
		comboBoxBuscar.setPrefWidth(400);
		comboBoxBuscar.setItems(control.getListaBusca());
		comboBoxBuscar.getSelectionModel().selectFirst();
		buscaPane.getChildren().addAll(comboBoxBuscar);
		
		Button btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction((e) -> {
			try {
				control.pesquisar();
				btnInserir.setDisable(true);
				btnAlterar.setDisable(false);
				btnExcluir.setDisable(false);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		Button btnLimpar = new Button("Limpar");
		btnLimpar.setOnAction((e) -> {
			try {
				this.atualizarTela();
				control.listar();
				table.setItems(control.getListaBusca());
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
				btnExcluir.setDisable(true);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnPesquisar.relocate(410, 0);
		btnLimpar.relocate(500, 0);
		
		buscaPane.getChildren().addAll(btnPesquisar, btnLimpar);
		panAutor.add(buscaPane, 1, 1);
		
		//Área IAE (Inserir, Alterar, Exluir
		
		panAutor.add(new Label("Código: "), 0, 2);
		panAutor.add(new Label("Nome: "), 0, 3);
		panAutor.add(new Label("Nascimento: "), 0, 4);
		panAutor.add(new Label("País: "), 0, 5);
		panAutor.add(new Label("Biografia: "), 2, 3);
		
		TextField txtCodigo = new TextField();
		txtCodigo.setEditable(false);
		panAutor.add(txtCodigo, 1, 2);
		TextField txtNome = new TextField();
		panAutor.add(txtNome, 1, 3);
		TextField txtNascimento = new TextField();
		panAutor.add(txtNascimento, 1, 4);
		TextField txtPais = new TextField();
		panAutor.add(txtPais, 1, 5);
		TextArea txtBiografia = new TextArea();
		txtBiografia.setPrefSize(100, 100);
		panAutor.add(txtBiografia, 3, 2, 3, 4);
		
		Pane iaePane = new Pane();
		
		btnInserir = new Button("Inserir");
		btnInserir.setOnAction((e) -> {
			try {
				control.inserir();
				new Alert(Alert.AlertType.INFORMATION, "Autor inserido com sucesso!").showAndWait();
				this.atualizarTela();
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
				btnExcluir.setDisable(true);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnAlterar = new Button("Alterar");
		btnAlterar.setOnAction((e) -> {
			try {
				control.alterar();
				new Alert(Alert.AlertType.INFORMATION, "Autor alterado com sucesso!").showAndWait();
				this.atualizarTela();
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
				btnExcluir.setDisable(true);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnExcluir = new Button("Excluir");
		btnExcluir.setOnAction((e) -> {
			try {
				control.deletar();
				new Alert(Alert.AlertType.INFORMATION, "Autor deletado com sucesso!").showAndWait();
				this.atualizarTela();
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
				btnExcluir.setDisable(true);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnInserir.setDisable(false);
		btnAlterar.setDisable(true);
		btnExcluir.setDisable(true);
		
		btnAlterar.relocate(70, 0);
		btnExcluir.relocate(145, 0);
		
		iaePane.getChildren().addAll(btnInserir, btnAlterar, btnExcluir);
		panAutor.add(iaePane, 3, 6);
		
		//Area da Tabela
		
		Pane panTabela = new Pane();
		
		Label lblPor = new Label("por");
		lblPor.relocate(230, 0);
		panTabela.getChildren().addAll(new Label("Filtrar"), lblPor);
		
		TextField txtFiltro = new TextField();
		txtFiltro.prefWidth(400);
		txtFiltro.relocate(50, 0);
		panTabela.getChildren().add(txtFiltro);
		
		ComboBox<String> comboBoxFiltro = new ComboBox<String>();
		comboBoxFiltro.getItems().clear();
		comboBoxFiltro.getItems().addAll("nome", "nascimento", "pais", "biografia");
		comboBoxFiltro.setPrefWidth(400);
		comboBoxFiltro.relocate(290, 0);
		panTabela.getChildren().add(comboBoxFiltro);
		
		Button btnListar = new Button("Listar");
		btnListar.setOnAction((e) -> {
			try {
				control.listarPorColuna();
				table.setItems(control.getLista());
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnListar.relocate(710, 0);
		panTabela.getChildren().add(btnListar);
		
		panAutor.add(panTabela, 0, 7, 8, 7);
		
		this.criarTabela();
		panAutor.add(table, 0, 9, 8, 9);
		
//		ColumnConstraints e Retorno
		ColumnConstraints colConstraint = new ColumnConstraints();
        colConstraint.setFillWidth(true);
        colConstraint.setHgrow(Priority.ALWAYS);
        
        panAutor.getColumnConstraints().addAll(new ColumnConstraints(), colConstraint, 
        		new ColumnConstraints(), colConstraint, new ColumnConstraints());	
		
		comboBoxBuscar.valueProperty().bindBidirectional(control.autorBusca);
		
		Bindings.bindBidirectional(txtCodigo.textProperty(), control.id, new NumberStringConverter());
	    Bindings.bindBidirectional(txtNome.textProperty(), control.nome);
	    Bindings.bindBidirectional(txtNascimento.textProperty(), control.nascimento);
	    Bindings.bindBidirectional(txtPais.textProperty(), control.pais);
	    Bindings.bindBidirectional(txtBiografia.textProperty(), control.biografia);
	    
	    Bindings.bindBidirectional(txtFiltro.textProperty(), control.filtro);
	    comboBoxFiltro.valueProperty().bindBidirectional(control.ColunaProperty());
	    
	    comboBoxBuscar.setItems(control.getListaBusca());
		comboBoxBuscar.getSelectionModel().select(0);
	    
		return panAutor;
	}
	
	/**
	 * atualiza a tela - limpa campos e primeira posição dos comboboxs
	 * @throws SQLException
	 */
	private void atualizarTela() throws SQLException{
		control.limpar();
		comboBoxBuscar.setItems(control.getListaBusca());
		comboBoxBuscar.getSelectionModel().select(0);
	}
	
	/**
	 * Cria a tabela principal do autor
	 */
	private void criarTabela(){
		table = new TableView<Autor>();
		table.setMaxSize(2000, 2000);
		
		TableColumn<Autor, String> col1 = new TableColumn<Autor, String>("Nome");
		col1.setCellValueFactory(
				new PropertyValueFactory<Autor,String>("nome")
		);
		
		TableColumn<Autor, String> col2 = new TableColumn<Autor, String>("Nascimento");
		col2.setCellValueFactory(
				new PropertyValueFactory<Autor, String>("dataNascimento")
		);
		
		TableColumn<Autor,String> col3 = new TableColumn<Autor, String>("Pais");
		col3.setCellValueFactory(
				new PropertyValueFactory<Autor,String>("pais")
		);
		TableColumn<Autor, String> col4 = new TableColumn<Autor, String>("Biografia");
		col4.setCellValueFactory(
				new PropertyValueFactory<Autor, String>("biografia")
		);
		
		table.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
			try {
				control.fromEntity(novo);
				btnInserir.setDisable(true);
				btnAlterar.setDisable(false);
				btnExcluir.setDisable(false);
			} catch (SQLException e) {
				new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
			}
		});
		
		table.getColumns().addAll(col1, col2, col3, col4);
		table.setItems(control.getLista());
				
	}
}
