package boundary;

import java.sql.SQLException;

import controller.UsuarioController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UsuarioBoundary extends Application{
	
	/** Classe para a tela dos livros.
	 * RNF01: USABILIDADE - interface gráfica simples
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */
	
	private TextField txtNome = new TextField();
	private TextField txtUsuario = new TextField();
	private TextField txtEmail = new TextField();
	private PasswordField txtSenha = new PasswordField();
	private PasswordField txtConfirmaSenha = new PasswordField();
	
	private Button btnCadastrar = new Button("Cadastrar");
	private Button btnVoltar = new Button("Voltar");
	
	private UsuarioController control;
	
	public UsuarioBoundary() {
		try {
			control = new UsuarioController();
		} catch (ClassNotFoundException | SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
	}
	/**
	 * Tela para a inseração de componentes para o cadastramento dos usuários
	 */
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane panPrincipal = new BorderPane();
		Scene scn = new Scene(panPrincipal, 320, 300);
		
		GridPane panCampos = new GridPane();
		
	    Bindings.bindBidirectional(txtNome.textProperty(), control.nome);
	    Bindings.bindBidirectional(txtUsuario.textProperty(), control.username);
	    Bindings.bindBidirectional(txtEmail.textProperty(), control.email);
	    Bindings.bindBidirectional(txtSenha.textProperty(), control.senha);
	    Bindings.bindBidirectional(txtConfirmaSenha.textProperty(), control.confirmaSenha);
				
		panCampos.add(new Label("Nome Completo"), 0, 0);
		panCampos.add(txtNome, 1, 0);
		panCampos.add(new Label("Usuário"), 0, 1);
		panCampos.add(txtUsuario, 1, 1);
		panCampos.add(new Label("E-mail"), 0, 2);
		panCampos.add(txtEmail, 1, 2);
		panCampos.add(new Label("Senha"), 0, 3);
		panCampos.add(txtSenha, 1, 3);
		panCampos.add(new Label("Confirmar a senha"), 0, 4);
		panCampos.add(txtConfirmaSenha, 1, 4);
		panCampos.setHgap(20);
		panCampos.setVgap(20);
		BorderPane.setMargin(panCampos, new Insets(10, 10, 10, 20));
		panPrincipal.setTop(panCampos);
		
		GridPane panButtons = new GridPane();
		panButtons.add(btnCadastrar, 1, 0);
		panButtons.add(btnVoltar, 2, 0);
		panButtons.setHgap(20);
		BorderPane.setMargin(panButtons, new Insets(10, 10, 10, 120));
		panPrincipal.setCenter(panButtons);
		
		btnCadastrar.setOnAction((e) -> {
			IdentificarBoundary telaId = new IdentificarBoundary();
			try {
				control.cadastrar();
				new Alert(Alert.AlertType.INFORMATION, "Cadastro realizado com sucesso!").showAndWait();
				telaId.start(new Stage());
				stage.close();
			} catch (Exception e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});

		btnVoltar.setOnAction((e) -> {
			IdentificarBoundary telaId = new IdentificarBoundary();
			try {
				telaId.start(new Stage());
				stage.close();
			} catch (Exception e1) {
				new Alert(Alert.AlertType.ERROR, "Erro: " + e1.getMessage()).showAndWait();
			}
		});
		
		stage.setScene(scn);
		stage.setTitle("Novo usuário");
		stage.show();	
	}
	
}
