package unoeste.fipp.dentalfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unoeste.fipp.dentalfx.db.dals.AgendaDAL;
import unoeste.fipp.dentalfx.db.dals.PessoaDal;
import unoeste.fipp.dentalfx.db.entidades.Agenda;
import unoeste.fipp.dentalfx.db.entidades.Dentista;
import unoeste.fipp.dentalfx.db.entidades.Horario;
import unoeste.fipp.dentalfx.db.entidades.Pessoa;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AgendamentoController implements Initializable {

    public static Dentista dentista;
    private Agenda agenda;

    @FXML
    private ComboBox<Dentista> cbDentista;

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
        if(cbDentista.getSelectionModel().getSelectedIndex() != -1){
            AgendaDAL agendaDAL = new AgendaDAL();
            Agenda agenda = agendaDAL.get(dentista,dpDiaConsulta.getValue());
            this.agenda = agenda;
            agenda.setData(dpDiaConsulta.getValue());
            tableView.setItems(FXCollections.observableArrayList(agenda.getHorarioList()));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PessoaDal p = new PessoaDal();
        colHora.setCellValueFactory(new PropertyValueFactory<>("sequencia"));
        colPaciente.setCellValueFactory(cellData -> {
            Horario horario = cellData.getValue();
            if(horario.getPaciente() != null)
                return new SimpleStringProperty(horario.getPaciente().getNome());
            else
                return new SimpleStringProperty("Sem pacientes cadastrados");
        });
        List<Pessoa> list= p.get("",new Dentista());
        List<Dentista> dentistaList = new ArrayList<>();
        for(Pessoa pessoa : list){
            dentistaList.add((Dentista) pessoa);
        }
        cbDentista.setItems(FXCollections.observableArrayList(dentistaList));
    }
    @FXML
    void onTrocouDentista(ActionEvent event) {
        dentista = cbDentista.getSelectionModel().getSelectedItem();
        if(dpDiaConsulta.getValue()!= null){
            AgendaDAL agendaDAL = new AgendaDAL();
            Agenda agenda = agendaDAL.get(dentista,dpDiaConsulta.getValue());
            this.agenda = agenda;
            agenda.setData(dpDiaConsulta.getValue());
            tableView.setItems(FXCollections.observableArrayList(agenda.getHorarioList()));
        }
    }

}
