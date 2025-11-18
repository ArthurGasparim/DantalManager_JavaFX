package unoeste.fipp.dentalfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import unoeste.fipp.dentalfx.db.dals.PessoaDal;
import unoeste.fipp.dentalfx.db.entidades.Dentista;
import unoeste.fipp.dentalfx.db.util.SingletonDB;
import unoeste.fipp.dentalfx.utils.LoginPanel;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;

public class DentalFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        do
        {
            LoginPanel.loginValido = true;
            LoginPanel.nivelAcesso = -1;
            Stage stageLogin = new Stage();
            stageLogin.initStyle(StageStyle.UTILITY);
            LoginPanel pLogin = new LoginPanel();
            pLogin.setPrefWidth(380);
            stageLogin.setScene(new Scene(pLogin));
            stageLogin.showAndWait();
        }while (!LoginPanel.loginValido);
        FXMLLoader fxmlLoader;
        if (LoginPanel.nivelAcesso > 0){
            if(LoginPanel.nivelAcesso == 3){
                System.out.println(LoginPanel.nome);
                String sql = "Select * from dentista WHERE den_nome LIKE '"+LoginPanel.nome+"'";
                ResultSet resultSet = SingletonDB.getConexao().consultar(sql);
                if (resultSet.next()){
                    Dentista dentista =new Dentista(resultSet.getInt("den_id"),resultSet.getString("den_nome"),resultSet.getInt("den_cro"),resultSet.getString("den_fone"),resultSet.getString("den_email"));
                    AgendamentoDentController.dentista = dentista;
                }

                fxmlLoader = new FXMLLoader(DentalFX.class.getResource("agendamentoDent-view.fxml"));
            }
            else {
                if (LoginPanel.nivelAcesso == 1)
                    fxmlLoader = new FXMLLoader(DentalFX.class.getResource("menu-view.fxml"));
                else
                    fxmlLoader = new FXMLLoader(DentalFX.class.getResource("menuSecre-view.fxml"));
                //montar tela dos betinhas
            }

            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Procedimentos!");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        }

    }

    public static void main(String[] args) {
        if(SingletonDB.conectar()) {
            launch();
        }
        else{
            int op=JOptionPane.showConfirmDialog(null,"Erro ao conectar:\n"+
                    SingletonDB.getConexao().getMensagemErro()+
                    "\n Deseja criar a base de dados?");
            if(op==JOptionPane.YES_OPTION){
                if(SingletonDB.criarDatabase("sisdentaldb","postgres","postgres123")) {
                    System.out.println("Database criado com sucesso");
                    //restaurar o script
                    SingletonDB.criarTabelas("sisdentaldb.sql","sisdentaldb");
                }
                else
                    System.out.println("Erro ao criar");
            }
            Platform.exit();
        }
    }
}