package unoeste.fipp.dentalfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unoeste.fipp.dentalfx.db.util.SingletonDB;

import javax.swing.*;
import java.io.IOException;

public class DentalFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Abrir o painel para login
        //Selecionar o fxml de acordo com o nível de usuário

        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Procedimentos!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
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