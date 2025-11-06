package unoeste.fipp.dentalfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AgendamentoController {

    @FXML
    private ComboBox<?> cbDentista;

    @FXML
    private TableColumn<?, ?> colHora;

    @FXML
    private TableColumn<?, ?> colPaciente;

    @FXML
    private DatePicker dpDiaConsulta;

    @FXML
    private TableView<?> tableView;

    @FXML
    void onAgendar(ActionEvent event) {

    }

    @FXML
    void onCancelarAgendamento(ActionEvent event) {

    }

    @FXML
    void onMaterial(ActionEvent event) {

    }

    @FXML
    void onMedico(ActionEvent event) {

    }

    @FXML
    void onPaciente(ActionEvent event) {

    }

    @FXML
    void onProcedimento(ActionEvent event) {

    }

    @FXML
    void onTrocouData(ActionEvent event) {

    }

    @FXML
    void onTrocouDentista(ActionEvent event) {

    }

}
