package unoeste.fipp.dentalfx.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.*;

import javafx.scene.layout.VBox;
import unoeste.fipp.dentalfx.db.entidades.Usuario;
import unoeste.fipp.dentalfx.db.util.SingletonDB;

import java.sql.ResultSet;

public class LoginPanel extends VBox {
    public static boolean loginValido = false;
    public static int nivelAcesso = 0;

    public LoginPanel(){
        super();
        setAlignment(Pos.CENTER);
        setPadding(new Insets(20, 20, 20, 20));
        Label loginLabel = new Label("Login:");
        loginLabel.setPadding(new Insets(0,0,10,0));
        TextField loginField = new TextField();
        Label passwordLabel = new Label("Senha:");
        passwordLabel.setPadding(new Insets(20, 0, 10, 0));
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Entrar");
        VBox.setMargin(loginButton, new Insets(30, 0, 0, 0));

        loginButton.setOnAction(event -> {
            String login = loginField.getText();
            String password = passwordField.getText();
            try {
                loginValido = verifyLogin(login, password);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            getScene().getWindow().hide();
        });

        getChildren().add(loginLabel);
        getChildren().add(loginField);
        getChildren().add(passwordLabel);
        getChildren().add(passwordField);
        getChildren().add(loginButton);

    }

    private boolean verifyLogin(String login, String password) throws Exception{
        ResultSet rs = SingletonDB.getConexao().consultar("Select * from usuario WHERE uso_nome LIKE '"+login+"'" );
        if(rs.next()){
            String senha = rs.getString("uso_senha");
            if(senha.equals(password)){
                nivelAcesso = rs.getInt("uso_nivel");
                return true;
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Senha incorreta");
                alert.showAndWait();
                return false;
            }

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Usuario nao existe");
            alert.showAndWait();
            return false;
        }

    }

    public boolean isLoginValido() {
        return loginValido;
    }

    public int getNivelAcesso() {
        return nivelAcesso;
    }
}

//coloque o seguinte código no método start do código de lançamento
//crie um novo stage, não use o que vem como parâmetro do start
//Stage stageLogin = new Stage();
//            stageLogin.initStyle(StageStyle.UTILITY);
//
//LoginPanel pLogin = new LoginPanel();
//            pLogin.setPrefWidth(380);
//            stageLogin.setScene(new Scene(pLogin));
//        stageLogin.showAndWait();
//            if (pLogin.isLoginValido()) {
//        // escolha o fxml de acordo com o nível de usuário, caso pertinente
//        // execute o lançamento do stage
//        }
