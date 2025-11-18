package unoeste.fipp.dentalfx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import unoeste.fipp.dentalfx.db.dals.MaterialDAL;
import unoeste.fipp.dentalfx.db.dals.ProcedimentoDAL;
import unoeste.fipp.dentalfx.db.entidades.Atendimento;
import unoeste.fipp.dentalfx.db.entidades.Material;
import unoeste.fipp.dentalfx.db.entidades.Procedimento;
import unoeste.fipp.dentalfx.db.util.SingletonDB;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProcItemController implements Initializable {
    public static int con_id;
    @FXML
    private TableColumn<Procedimento, String> colDesc;

    @FXML
    private TableColumn<Procedimento, Integer> colID;

    @FXML
    private TableView<Procedimento> tableView;

    @FXML
    private TextField tfQuant;

    @FXML
    void onCancelar(ActionEvent event) {
        tfQuant.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedIndex() != -1){
            AlteraAgendamentoView.horario.getAtendimento().getProcedimentoList().add(new Atendimento.ProcItem(tableView.getSelectionModel().getSelectedItem(),Integer.parseInt(tfQuant.getText())));
            tfQuant.getScene().getWindow().hide();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        carregarTabela();
    }

    private void carregarTabela() {
        ProcedimentoDAL dal = new ProcedimentoDAL();
        List<Procedimento> materialList = dal.get("");
        tableView.setItems(FXCollections.observableArrayList(materialList));
    }
}
