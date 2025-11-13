package unoeste.fipp.dentalfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import unoeste.fipp.dentalfx.db.dals.PessoaDal;
import unoeste.fipp.dentalfx.db.entidades.Dentista;
import unoeste.fipp.dentalfx.db.entidades.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioFormController implements Initializable {
    public static Usuario usuario;
    public TextField tfId;
    public TextField tfNome;
    public TextField tfNovel;
    public TextField tfSenha;

    public void onConfirmar(ActionEvent actionEvent) {
        PessoaDal dal = new PessoaDal();
        Usuario alterar;
        if(usuario != null)
            alterar = new Usuario(usuario.getId(),tfNome.getText(),Integer.parseInt(tfNovel.getText()),tfSenha.getText());
        else{
            alterar = new Usuario(tfNome.getText(),Integer.parseInt(tfNovel.getText()),tfSenha.getText());
        }
        tfNome.getScene().getWindow().hide();
        if(usuario == null)
            dal.gravar(alterar);
        else{
            dal.alterar(alterar);
            System.out.println("Alterando");
        }

    }

    public void onCancelar(ActionEvent actionEvent) {
        tfSenha.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(usuario != null){

            tfId.setText(usuario.getId()+"");
            tfNome.setText(usuario.getNome());
            tfNovel.setText(usuario.getNivel()+"");
            tfSenha.setText(usuario.getSenha());
        }
        Platform.runLater(()->{tfNome.requestFocus();});
    }
}
