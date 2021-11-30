package boundary;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PrincipalBoundary extends Application{
	
	/** Classe para o menu principal que posibilita a transição entre as telas de 
	 * autor, editora, livro e edicao.
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
	private AutorBoundary telaAutor;
	private EditoraBoundary telaEditora;
	private EdicaoBoundary telaEdicao;
	private LivroBoundary telaLivro;
	
	private String nomeFuncionario;
	
	/**
	 * Método que recebe o primeiro nome do usuário que entrou no sistema pela identificação
	 * @param nome - primeiro nome do usuário
	 */
	public PrincipalBoundary(String nome) {
		this.nomeFuncionario = nome;
	}
	
	/**
	 * Método que cria um menu com botões para a transição de telas
	 */
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane panPrincipal = new BorderPane();
		Scene scn = new Scene(panPrincipal, 1000, 800);
		
		GridPane menu = new GridPane();
		BorderPane.setMargin(menu, new Insets(10, 10, 10, 10));
		menu.setVgap(20);
		
		Button btnPedidos = new Button("Pedidos");
		btnPedidos.setPrefWidth(100);
		Button btnLivros = new Button("Livros");
		btnLivros.setPrefWidth(100);
		Button btnAutor = new Button("Autor");
		btnAutor.setPrefWidth(100);
		Button btnEditora = new Button("Editora");
		btnEditora.setPrefWidth(100);
		Button btnEdicao= new Button("Edição");
		btnEdicao.setPrefWidth(100);
		Button btnSair = new Button("Sair");
		btnSair.setPrefWidth(100);
		
		btnLivros.setOnAction((e) -> {
			telaLivro = new LivroBoundary();
			panPrincipal.setCenter(telaLivro.montarTelaLivro());
			stage.setTitle("Área dos Livros");
		});
		
		btnAutor.setOnAction((e) -> {
			telaAutor = new AutorBoundary();
			panPrincipal.setCenter(telaAutor.montarTelaAutor());
			stage.setTitle("Área dos Autores");
		});
		
		btnEditora.setOnAction((e) -> {
			telaEditora = new EditoraBoundary();
			panPrincipal.setCenter(telaEditora.montarTelaEditora());
			stage.setTitle("Área das Editoras");
		});
		
		btnEdicao.setOnAction((e) -> {
			telaEdicao = new EdicaoBoundary();
			panPrincipal.setCenter(telaEdicao.montarTelaEdicao());
			stage.setTitle("Área Edição de Livros");
		});
		
		
		btnSair.setOnAction((e) -> {
			IdentificarBoundary telaId = new IdentificarBoundary();
			try {
				telaId.start(new Stage());
				stage.close();
			} catch (Exception e1) {
				new Alert(Alert.AlertType.ERROR, 
						"Erro: " + e1.getMessage()).showAndWait();
			}
		});		
		
		menu.add(new Label("Projeto Livraria"), 0, 0);
		menu.add(new Label("Olá " + nomeFuncionario), 0, 1);
		menu.add(btnLivros, 0, 3);
		menu.add(btnAutor, 0, 4);
		menu.add(btnEditora, 0, 5);
		menu.add(btnEdicao, 0, 6);
		menu.add(btnSair, 0, 7);
		
		
		panPrincipal.setLeft(menu);
		telaLivro = new LivroBoundary();
		panPrincipal.setCenter(telaLivro.montarTelaLivro());
		
		stage.setScene(scn);
		stage.setMaximized(true);
		stage.setTitle("Área dos Livros");
		stage.show();
		
	}

}
