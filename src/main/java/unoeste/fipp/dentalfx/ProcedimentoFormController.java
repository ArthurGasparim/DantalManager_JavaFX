package unoeste.fipp.dentalfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import unoeste.fipp.dentalfx.db.dals.MaterialDAL;
import unoeste.fipp.dentalfx.db.dals.ProcedimentoDAL;
import unoeste.fipp.dentalfx.db.entidades.Material;
import unoeste.fipp.dentalfx.db.entidades.Procedimento;
import unoeste.fipp.dentalfx.utils.MaskFieldUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class ProcedimentoFormController implements Initializable {
    public TextField tfId;
    public TextField tfDesc;
    public TextField tfPreco;
    public static Procedimento procedimento;
    public TextField tfTempo;

    public void onConfirmar(ActionEvent actionEvent) {
        ProcedimentoDAL dal = new ProcedimentoDAL();
        Procedimento alterar;
        if(procedimento != null)
            alterar = new Procedimento(procedimento.getId(),Double.parseDouble( tfTempo.getText()),Double.parseDouble( tfPreco.getText()),tfDesc.getText());
        else{
            alterar = new Procedimento(Double.parseDouble( tfTempo.getText()),Double.parseDouble( tfPreco.getText()),tfDesc.getText());
        }
        tfPreco.getScene().getWindow().hide();
        if(procedimento == null)
            dal.gravar(alterar);
        else
            dal.alterar(alterar);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(procedimento != null){

            tfId.setText(procedimento.getId()+"");
            tfDesc.setText(procedimento.getDescricao());
            tfPreco.setText(procedimento.getValor()+"");
            tfTempo.setText(procedimento.getTempo()+"");
        }
        MaskFieldUtil.monetaryField(tfPreco);
        Platform.runLater(()->{tfDesc.requestFocus();});
    }

    public void onCancelar(ActionEvent actionEvent) {
        tfPreco.getScene().getWindow().hide();
    }
}
