package boundary;

import java.sql.SQLException;

import controller.EditoraController;
import entity.Editora;
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

public class EditoraBoundary {
	
	/** Classe para a tela das editoras.
	 * RNF01: USABILIDADE - interface gráfica simples
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	GridPane panEditora;
	
	TableView<Editora> table;
	
	Button btnInserir;
	Button btnAlterar;
	Button btnExcluir;
	Button btnPesquisar;
	Button btnLimpar;
	Button btnListar;
	
	TextField txtCNPJ;
	TextField txtNome;
	TextField txtEmail;
	TextField txtTelefone;
	TextField txtLogradouro;
	TextField txtNum;
	TextField txtComplemento;
	TextField txtCEP;
	TextField txtFiltro;
	
	ComboBox<Editora> comboBoxBuscar;
	ComboBox<String> comboBoxFiltro;
	
	ColumnConstraints colConstraint;
	
	EditoraController control;
	
	public EditoraBoundary(){
		
		comboBoxBuscar = new ComboBox<Editora>(); comboBoxBuscar.setPrefWidth(400);
		comboBoxFiltro = new ComboBox<String>(); comboBoxFiltro.setPrefWidth(400);
		
		btnPesquisar = new Button("Pesquisar");
		btnLimpar = new Button("Limpar");
		btnInserir = new Button("Inserir");
		btnAlterar = new Button("Alterar");
		btnExcluir = new Button("Excluir");
		btnListar = new Button("Listar");
		
		txtCNPJ = new TextField();
		txtNome = new TextField();
		txtEmail = new TextField();
		txtTelefone = new TextField();
		txtLogradouro = new TextField();
		txtNum = new TextField();
		txtComplemento = new TextField();
		txtCEP = new TextField();
		txtFiltro = new TextField();
		
		colConstraint = new ColumnConstraints();
		
		try {
			control = new EditoraController();
		} catch (ClassNotFoundException | SQLException e2) {
			new Alert(Alert.AlertType.ERROR, e2.getMessage()).showAndWait();
		}	
	}
	/**
	 * Método que monta a tela das editoras
	 * @return panEditora - painel com todos os componentes da editora inseridos
	 */
	public GridPane montarTelaEditora(){
	
		panEditora = new GridPane();
		BorderPane.setMargin(panEditora, new Insets(10, 10, 10, 10));
		panEditora.setHgap(15);
		panEditora.setVgap(20);
		panEditora.setPrefSize(1000, 800);
		
		Bindings.bindBidirectional(txtCNPJ.textProperty(), control.cnpj);
	    Bindings.bindBidirectional(txtNome.textProperty(), control.nome);
	    Bindings.bindBidirectional(txtEmail.textProperty(), control.email);
	    Bindings.bindBidirectional(txtTelefone.textProperty(), control.telefone);
	    Bindings.bindBidirectional(txtLogradouro.textProperty(), control.logradouro);
	    Bindings.bindBidirectional(txtNum.textProperty(), control.numero, new NumberStringConverter());
	    Bindings.bindBidirectional(txtComplemento.textProperty(), control.complemento);
	    Bindings.bindBidirectional(txtCEP.textProperty(), control.cep);
	    Bindings.bindBidirectional(txtFiltro.textProperty(), control.filtro);
	    comboBoxBuscar.valueProperty().bindBidirectional(control.editora);
		comboBoxFiltro.valueProperty().bindBidirectional(control.ColunaProperty());
	    
		//Área Busca
		
		panEditora.add(new Label("Buscar: "), 0, 1);
		
		Pane buscaPane = new Pane();
		
		
		comboBoxBuscar.setItems(control.getListaBusca());
		comboBoxBuscar.getSelectionModel().select(0);
		buscaPane.getChildren().addAll(comboBoxBuscar);
		
		btnPesquisar.relocate(410, 0);
		btnLimpar.relocate(500, 0);
		
		buscaPane.getChildren().addAll(btnPesquisar, btnLimpar);
		panEditora.add(buscaPane, 1, 1);
		
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
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
				btnExcluir.setDisable(true);
		});
		//Área IAE (Inserir, Alterar, Exluir
		
		panEditora.add(new Label("CNPJ: "), 0, 2);
		panEditora.add(new Label("Nome: "), 0, 3);
		panEditora.add(new Label("E-mail: "), 0, 4);
		panEditora.add(new Label("Telefone: "), 0, 5);
		panEditora.add(new Label("Logradouro: "), 2, 2);
		panEditora.add(new Label("Número: "), 2, 3);
		panEditora.add(new Label("Complemento: "), 2, 4);
		panEditora.add(new Label("CEP: "), 2, 5);
		
		panEditora.add(txtCNPJ, 1, 2);
		panEditora.add(txtNome, 1, 3);
		panEditora.add(txtEmail, 1, 4);
		panEditora.add(txtTelefone, 1, 5);
		panEditora.add(txtLogradouro, 3, 2);
		panEditora.add(txtNum, 3, 3);
		panEditora.add(txtComplemento, 3, 4);
		panEditora.add(txtCEP, 3, 5);
		
		Pane iaePane = new Pane();
		
		btnAlterar.relocate(70, 0);
		btnExcluir.relocate(145, 0);
		
		btnInserir.setDisable(false);
		btnAlterar.setDisable(true);
		btnExcluir.setDisable(true);
		
		iaePane.getChildren().addAll(btnInserir, btnAlterar, btnExcluir);
		panEditora.add(iaePane, 3, 6);
		
		btnInserir.setOnAction((e) ->{
			try {
				control.adicionar();
				new Alert(Alert.AlertType.INFORMATION, "Editora inserida com sucesso!").showAndWait();
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
				new Alert(Alert.AlertType.INFORMATION, "Editora alterada com sucesso!").showAndWait();
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
				new Alert(Alert.AlertType.INFORMATION, "Editora deletada com sucesso!").showAndWait();
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
		comboBoxFiltro.getItems().addAll("nome", "email", "telefone", "logradouro_end", "numero_end", "complemento_end", "cep");
		comboBoxFiltro.relocate(290, 0);
		panTabela.getChildren().add(comboBoxFiltro);
		
		btnListar.relocate(710, 0);
		panTabela.getChildren().add(btnListar);
		btnListar.setOnAction((e) ->{
			try {
				control.listarPorColuna();
				table.setItems(control.getListaEditoras());
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		
		panEditora.add(panTabela, 0, 7, 8, 7);
		this.criarTabela();
		
		//ColumnConstraints e Retorno
		
        colConstraint.setFillWidth(true);
        colConstraint.setHgrow(Priority.ALWAYS);

		panEditora.getColumnConstraints().addAll(new ColumnConstraints(), colConstraint, new ColumnConstraints(), colConstraint, new ColumnConstraints());
		
		return panEditora;
	}
	
	private void atualizarTela() throws SQLException{
		control.limpar();
		comboBoxBuscar.setItems(control.getListaBusca());
		comboBoxBuscar.getSelectionModel().selectFirst();		
	}
	
	private void criarTabela(){
		table = new TableView<Editora>();
		
		table.setMaxSize(2000, 2000);
		panEditora.add(table, 0, 9, 8, 9);
		
		TableColumn<Editora, Integer> col1 = new TableColumn<>("CNPJ");
        col1.setCellValueFactory(
                new PropertyValueFactory<Editora, Integer>("cnpj")
        );
        
		TableColumn<Editora, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory(
                new PropertyValueFactory<Editora, String>("nome")
        );
        
        TableColumn<Editora, String> col3 = new TableColumn<>("E-mail");
        col3.setCellValueFactory(
                new PropertyValueFactory<Editora, String>("email")
        );
        
        TableColumn<Editora, String> col4 = new TableColumn<>("Telefone");
        col4.setCellValueFactory(
                new PropertyValueFactory<Editora, String>("telefone")
        );
        
        TableColumn<Editora, String> col5 = new TableColumn<>("Logradouro");
        col5.setCellValueFactory(
                new PropertyValueFactory<Editora, String>("logradouro")
        );
        
        TableColumn<Editora, Integer> col6 = new TableColumn<>("Numero");
        col6.setCellValueFactory(
                new PropertyValueFactory<Editora, Integer>("numero")
        );
        
        TableColumn<Editora, String> col7 = new TableColumn<>("Complemento");
        col7.setCellValueFactory(
                new PropertyValueFactory<Editora, String>("complemento")
        );
        
        TableColumn<Editora, String> col8 = new TableColumn<>("CEP");
        col8.setCellValueFactory(
                new PropertyValueFactory<Editora, String>("cep")
        );
        
        table.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
				control.fromEntity(novo);
				btnInserir.setDisable(true);
				btnAlterar.setDisable(false);
				btnExcluir.setDisable(false);
        });
        
        table.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8);
        table.setItems(control.getListaEditoras());
	}
}
