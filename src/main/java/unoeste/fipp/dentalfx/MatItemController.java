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
import unoeste.fipp.dentalfx.db.entidades.Atendimento;
import unoeste.fipp.dentalfx.db.entidades.Material;
import unoeste.fipp.dentalfx.db.util.SingletonDB;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MatItemController implements Initializable {
    public static int con_id;
    @FXML
    private TableColumn<Material, String> colDesc;

    @FXML
    private TableColumn<Material, Integer> colID;

    @FXML
    private TableView<Material> tableView;

    @FXML
    private TextField tfQuant;

    @FXML
    void onCancelar(ActionEvent event) {
        tfQuant.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedIndex() != -1){
            AlteraAgendamentoView.horario.getAtendimento().getMaterialList().add(new Atendimento.MatItem(tableView.getSelectionModel().getSelectedItem(),Integer.parseInt(tfQuant.getText())));
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
        MaterialDAL dal = new MaterialDAL();
        List<Material> materialList = dal.get("");
        tableView.setItems(FXCollections.observableArrayList(materialList));
    }
}
