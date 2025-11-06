package unoeste.fipp.dentalfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import unoeste.fipp.dentalfx.db.dals.MaterialDAL;
import unoeste.fipp.dentalfx.db.entidades.Material;
import unoeste.fipp.dentalfx.utils.MaskFieldUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class MaterialFormController implements Initializable {
    public TextField tfId;
    public TextField tfDesc;
    public TextField tfPreco;
    public static Material material;

    public void onConfirmar(ActionEvent actionEvent) {
        MaterialDAL dal = new MaterialDAL();
        Material alterar;
        if(material != null)
            alterar = new Material(material.getId(),tfDesc.getText(),Double.parseDouble( tfPreco.getText()));
        else{
            alterar = new Material(tfDesc.getText(),Double.parseDouble( tfPreco.getText()));
        }
        tfPreco.getScene().getWindow().hide();
        if(material == null)
            dal.gravar(alterar);
        else
            dal.alterar(alterar);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(material != null){

            tfId.setText(material.getId()+"");
            tfDesc.setText(material.getDescricao());
            tfPreco.setText(material.getValor()+"");
        }
        MaskFieldUtil.monetaryField(tfPreco);
            Platform.runLater(()->{tfDesc.requestFocus();});
    }

    public void onCancelar(ActionEvent actionEvent) {
        tfPreco.getScene().getWindow().hide();
    }
}
