package unoeste.fipp.dentalfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

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
    void onMaterial(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("material/material-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Material");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void onMedico(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("dentista/dentista-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dentista");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void onPaciente(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("paciente/paciente-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Paciente");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void onProcedimento(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("procedimento/procedimento-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Procedimento");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void onTrocouData(ActionEvent event) {

    }

    @FXML
    void onTrocouDentista(ActionEvent event) {

    }

}
