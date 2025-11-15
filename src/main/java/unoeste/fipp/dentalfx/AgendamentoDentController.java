package unoeste.fipp.dentalfx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import unoeste.fipp.dentalfx.db.dals.AgendaDAL;
import unoeste.fipp.dentalfx.db.entidades.Agenda;
import unoeste.fipp.dentalfx.db.entidades.Dentista;
import unoeste.fipp.dentalfx.db.entidades.Horario;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AgendamentoDentController implements Initializable {

    public static Dentista dentista;
    public Label dentistaLabel;
    @FXML
    private TableColumn<Horario, Integer> colHora;

    @FXML
    private TableColumn<Horario, String> colPaciente;

    @FXML
    private DatePicker dpDiaConsulta;

    @FXML
    private TableView<Horario> tableView;

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
        AgendaDAL agendaDAL = new AgendaDAL();
        Agenda agenda = agendaDAL.get(dentista,dpDiaConsulta.getValue());
        tableView.setItems(FXCollections.observableArrayList(agenda.getHorarioList()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dentistaLabel.setText(dentista.getNome());
        colHora.setCellValueFactory(new PropertyValueFactory<>("sequencia"));
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
    }
}
