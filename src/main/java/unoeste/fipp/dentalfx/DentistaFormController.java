package unoeste.fipp.dentalfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import unoeste.fipp.dentalfx.db.dals.MaterialDAL;
import unoeste.fipp.dentalfx.db.dals.PessoaDal;
import unoeste.fipp.dentalfx.db.entidades.Dentista;
import unoeste.fipp.dentalfx.db.entidades.Material;
import unoeste.fipp.dentalfx.db.entidades.Paciente;
import unoeste.fipp.dentalfx.utils.MaskFieldUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class DentistaFormController implements Initializable {


    public static Dentista dentista;

    @FXML
    private TextField tfCro;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfFone;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNome;

    @FXML
    void onCancelar(ActionEvent event) {
        dentista =null;
        tfNome.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        PessoaDal dal = new PessoaDal();
        Dentista alterar;
        if(dentista != null)
            alterar = new Dentista(dentista.getId(),dentista.getNome(),dentista.getCro(),dentista.getFone(),dentista.getEmail());
        else{
            alterar = new Dentista(tfNome.getText(),Integer.parseInt(tfCro.getText()),tfFone.getText(),tfEmail.getText());
        }
        tfNome.getScene().getWindow().hide();
        if(dentista == null)
            dal.gravar(alterar);
        else
            dal.alterar(alterar);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(dentista != null){

            tfId.setText(dentista.getId()+"");
            tfNome.setText(dentista.getNome());
            tfCro.setText(dentista.getCro()+"");
            tfEmail.setText(dentista.getEmail());
            tfFone.setText(dentista.getFone());
        }
        Platform.runLater(()->{tfNome.requestFocus();});
    }
}
