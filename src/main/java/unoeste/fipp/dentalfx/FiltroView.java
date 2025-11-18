package unoeste.fipp.dentalfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

public class FiltroView {

    public static LocalDate dateIni, dateFim;
    public static int id;

    @FXML
    private DatePicker dpFinal;

    @FXML
    private DatePicker dpIni;

    @FXML
    private TextField tfId;

    @FXML
    void onConfirmar(ActionEvent event) {
        if(tfId.getText()!="" && dpIni.getValue() != null && dpFinal.getValue() != null){
            dateFim = dpFinal.getValue();
            dateIni = dpIni.getValue();
            id = Integer.parseInt(tfId.getText());
            tfId.getScene().getWindow().hide();
        }

    }

}
