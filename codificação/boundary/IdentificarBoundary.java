package boundary;

import java.sql.SQLException;

import controller.UsuarioController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class IdentificarBoundary extends Application {
	
	/** Classe para a tela de identificação do usuário por login e senha.
	 * RNF02: SEGURANÇA - Identificar Usuário
	 * @author Camila Cecília e Gustavo Cavichioli
	 * @version 1.0
	 */

	private TextField txtUsuario = new TextField();
	private PasswordField txtSenha = new PasswordField();

	private Button btnEntrar = new Button("Entrar");
	private Button btnCadastrar = new Button("Cadastrar");
	private Button btnRecuperar = new Button("Recuperar");

	private UsuarioController control;
	
	private PrincipalBoundary telaPrincipal;
	private UsuarioBoundary telaNovoUsuario = new UsuarioBoundary();

	public IdentificarBoundary() {
		try {
			control = new UsuarioController();
		} catch (ClassNotFoundException | SQLException e) {
			new Alert(Alert.AlertType.ERROR, "Erro: " + e.getMessage()).showAndWait();
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane panPrincipal = new BorderPane();
		Scene scn = new Scene(panPrincipal, 300, 200);
		
		Bindings.bindBidirectional(txtUsuario.textProperty(), control.username);
	    Bindings.bindBidirectional(txtSenha.textProperty(), control.senha);

		GridPane panId = new GridPane();
		BorderPane.setMargin(panId, new Insets(10, 10, 10, 30));
		panId.add(new Label("Sistema Livraria"), 1, 0);
		panId.add(new Label("Usuário"), 0, 1);
		panId.add(txtUsuario, 1, 1);
		panId.add(new Label("Senha"), 0, 2);
		panId.add(txtSenha, 1, 2);
		panId.setVgap(20);
		panId.setHgap(20);
		BorderPane.setAlignment(panId, Pos.CENTER);
		panPrincipal.setTop(panId);

		GridPane panButtons = new GridPane();
		panButtons.add(btnEntrar, 1, 0);
		panButtons.add(btnCadastrar, 2, 0);
		panButtons.add(btnRecuperar, 3, 0);
		panButtons.setHgap(20);
		BorderPane.setMargin(panButtons, new Insets(10, 10, 10, 10));
		panPrincipal.setCenter(panButtons);

		stage.setScene(scn);
		stage.setTitle("Identificação");
		stage.show();
		
		btnEntrar.setOnAction((e) -> {
			try {
				if(control.validar()){
					telaPrincipal = new PrincipalBoundary(control.getNomePrimeiro());
					telaPrincipal.start(new Stage());
					stage.close();
				}
			} catch (Exception e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnCadastrar.setOnAction((e) -> {
			try {
				telaNovoUsuario.start(new Stage());
				stage.close();
			} catch (Exception e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}

		});

		btnRecuperar.setOnAction((e) -> {
			TextInputDialog tidEmail = new TextInputDialog();
			tidEmail.setHeaderText("Digite o e-mail para recuperar a senha:");
			tidEmail.showAndWait();
			String email = tidEmail.getEditor().getText();
			try {
				control.recuperar(email);
				new Alert(Alert.AlertType.INFORMATION,
						"E-MAIL ENCONTRADO: " + email + "\nFoi enviado os dados para a recuperação!").showAndWait();
			} catch (Exception e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}

		});
	}

	public static void main(String[] args) {
		Application.launch(IdentificarBoundary.class, args);
	}
}
