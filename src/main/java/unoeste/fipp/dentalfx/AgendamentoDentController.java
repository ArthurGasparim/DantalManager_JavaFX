package unoeste.fipp.dentalfx;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import unoeste.fipp.dentalfx.db.dals.AgendaDAL;
import unoeste.fipp.dentalfx.db.entidades.Agenda;
import unoeste.fipp.dentalfx.db.entidades.Dentista;
import unoeste.fipp.dentalfx.db.entidades.Horario;
import unoeste.fipp.dentalfx.db.entidades.Paciente;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AgendamentoDentController implements Initializable {

    public static Dentista dentista;
    private Agenda agenda;
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
    void onAgendar(ActionEvent event) throws Exception{
        AgendaDAL dal = new AgendaDAL();
        dal.gravar(agenda);
        dpDiaConsulta.setValue(null);
        tableView.setItems(null);
    }

    @FXML
    void onCancelarAgendamento(ActionEvent event) throws Exception{
        if(tableView.getSelectionModel().getSelectedIndex() != -1) {
            Stage stage = new Stage();
            AlteraAgendamentoView.horario = tableView.getSelectionModel().getSelectedItem();
            AlteraAgendamentoView.dentista = dentista;
            AlteraAgendamentoView.date = dpDiaConsulta.getValue();
            FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("altera-agendamento-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Aletrar Agendamento");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            if(AlteraAgendamentoView.horariofinal.getSequencia() != -1) {
                agenda.addHorario(new Horario(AlteraAgendamentoView.horario.getSequencia(), null), AlteraAgendamentoView.horario.getSequencia());
                if (AlteraAgendamentoView.horariofinal.getSequencia() > 0) {
                    agenda.addHorario(AlteraAgendamentoView.horariofinal, AlteraAgendamentoView.horariofinal.getSequencia());

                }
            }
            tableView.setItems(FXCollections.observableArrayList(agenda.getHorarioList()));

        }
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
        this.agenda = agenda;
        agenda.setData(dpDiaConsulta.getValue());
        tableView.setItems(FXCollections.observableArrayList(agenda.getHorarioList()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dentistaLabel.setText(dentista.getNome());
        colHora.setCellValueFactory(new PropertyValueFactory<>("sequencia"));
       colPaciente.setCellValueFactory(cellData -> {
           Horario horario = cellData.getValue();
           if(horario.getPaciente() != null)
             return new SimpleStringProperty(horario.getPaciente().getNome());
           else
               return new SimpleStringProperty("Sem pacientes cadastrados");
       });

    }

    public void onSobre(ActionEvent actionEvent)throws Exception {
        HelpController.path = "sobre.html";
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("help-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Ajuda do DentalFX");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onTopicos(ActionEvent actionEvent) throws Exception{
        HelpController.path = "main.html";
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("help-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Ajuda do DentalFX");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
