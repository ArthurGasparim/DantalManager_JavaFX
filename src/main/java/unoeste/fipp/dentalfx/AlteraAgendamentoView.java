package unoeste.fipp.dentalfx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unoeste.fipp.dentalfx.db.dals.PessoaDal;
import unoeste.fipp.dentalfx.db.entidades.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AlteraAgendamentoView implements Initializable {
    public static Horario horario;
    public static Dentista dentista;
    public static LocalDate date;
    public static Horario horariofinal;
    @FXML
    public TableColumn<Atendimento.MatItem,Integer> ColMatQuant;

    @FXML
    public TableColumn<Atendimento.ProcItem,Integer> ColProcQuant;
    public TextField tfRelato;
    public TextField tfPaciente;
    public CheckBox toggleEf;

    @FXML
    private TableColumn<Atendimento.MatItem,String> ColMatNome;

    @FXML
    private TableColumn<Atendimento.ProcItem,Integer> ColProcID;

    @FXML
    private TableColumn<Atendimento.ProcItem,String> ColProcNome;

    @FXML
    private TableColumn<Atendimento.MatItem,Integer> colMatID;

    @FXML
    private Label lbData;

    @FXML
    private Label lbNomeDent;

    @FXML
    private TableView<Atendimento.MatItem> tbMaterial;

    @FXML
    private TableView<Atendimento.ProcItem> tbProc;

    @FXML
    private TextField tfHorario;

    @FXML
    void onCancelar(ActionEvent event) {
        horariofinal = new Horario(-1,null);
        lbData.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        if(!tfPaciente.getText().equals("")){
            PessoaDal dal = new PessoaDal();
            List<Pessoa> list = dal.get("pac_nome LIKE '"+tfPaciente.getText()+"'",new Paciente());
            if(list.size() > 0 ){
                Paciente paciente = (Paciente) dal.get(list.get(0).getId(),new Paciente());
                horariofinal = new Horario(Integer.parseInt(tfHorario.getText()),paciente);
                Atendimento atendimento = new Atendimento(tfRelato.getText(),toggleEf.isSelected());
                atendimento.setMaterialList(tbMaterial.getItems());
                atendimento.setProcedimentoList(tbProc.getItems());
                horariofinal.setAtendimento(atendimento);
                horariofinal.setConId(horario.getConId());
                lbData.getScene().getWindow().hide();
        }

        }

    }

    @FXML
    void onExcluir(ActionEvent event) {
        horariofinal = new Horario(-2,null);
        lbData.getScene().getWindow().hide();

    }

    public void carregarTabelas(){
        tbMaterial.setItems(FXCollections.observableArrayList(horario.getAtendimento().getMaterialList()));
        tbProc.setItems(FXCollections.observableArrayList(horario.getAtendimento().getProcedimentoList()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(horario.getAtendimento() != null){


            lbNomeDent.setText(dentista.getNome());
            lbData.setText(date.toString());
            tfHorario.setText(horario.getSequencia()+"");
            tfPaciente.setText(horario.getPaciente().getNome());
            tfRelato.setText(horario.getAtendimento().getRelato());
            carregarTabelas();
            if(horario.getAtendimento().efetivado())
                toggleEf.setSelected(true);
        }else{
            horario.setAtendimento(new Atendimento("Teste",true));
        }
        tfHorario.setText(horario.getSequencia()+"");
        ColMatQuant.setCellValueFactory(celData ->{
            return new SimpleObjectProperty<>(celData.getValue().quant());
        });
        colMatID.setCellValueFactory(cellData -> {
            Atendimento.MatItem matItem = cellData.getValue();
            if(matItem.material() != null)
                return new SimpleObjectProperty(matItem.material().getId());
            else
                return new SimpleObjectProperty(0);
        });
        ColMatNome.setCellValueFactory(cellData -> {
            Atendimento.MatItem matItem = cellData.getValue();
            if(matItem.material() != null)
                return new SimpleObjectProperty(matItem.material().getDescricao());
            else
                return new SimpleObjectProperty("");
        });
        ColProcQuant.setCellValueFactory(celData ->{
            return new SimpleObjectProperty<>(celData.getValue().quant());
        });
        ColProcID.setCellValueFactory(cellData -> {
            Atendimento.ProcItem matItem = cellData.getValue();
            if(matItem.procedimento() != null)
                return new SimpleObjectProperty(matItem.procedimento().getId());
            else
                return new SimpleObjectProperty(0);
        });
        ColProcNome.setCellValueFactory(cellData -> {
            Atendimento.ProcItem matItem = cellData.getValue();
            if(matItem.procedimento() != null)
                return new SimpleObjectProperty(matItem.procedimento().getDescricao());
            else
                return new SimpleObjectProperty("");
        });
    }

    public void onAddMatItem(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        MatItemController.con_id = horario.getConId();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("matItem-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Adicionar Maateriais");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        carregarTabelas();
    }

    public void onAddProcItem(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        MatItemController.con_id = horario.getConId();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("ProcItemView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Adicionar Procedimentos");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        carregarTabelas();
    }

    public void onRmvMatItem(ActionEvent actionEvent) {
        if(tbMaterial.getSelectionModel().getSelectedIndex()!= -1){
            horario.getAtendimento().getMaterialList().remove(tbMaterial.getSelectionModel().getSelectedItem());
            carregarTabelas();
        }
    }

    public void onRmvProcItem(ActionEvent actionEvent) {
        if(tbProc.getSelectionModel().getSelectedIndex()!= -1){
            horario.getAtendimento().getProcedimentoList().remove(tbProc.getSelectionModel().getSelectedItem());
            carregarTabelas();
        }
    }
}
