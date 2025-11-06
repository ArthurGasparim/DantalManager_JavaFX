package unoeste.fipp.dentalfx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unoeste.fipp.dentalfx.db.dals.MaterialDAL;
import unoeste.fipp.dentalfx.db.entidades.Material;
import unoeste.fipp.dentalfx.db.util.SingletonDB;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MaterialTableController implements Initializable {

    public TableView<Material> table;
    @FXML
    private TextField TextField;

    @FXML
    private TableColumn<Material, Double> colPreco;

    @FXML
    private TableColumn<Material, String> coldesc;

    @FXML
    private TableColumn<Material, Integer> colid;

    @FXML
    void onBusca(KeyEvent event) {
        carregarTabela("mat_desc LIKE '%"+TextField.getText()+"%'");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coldesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("valor"));
        carregarTabela("");
    }

    private void carregarTabela(String filtro) {
        MaterialDAL dal = new MaterialDAL();
        List<Material> materialList = dal.get(filtro);
        table.setItems(FXCollections.observableArrayList(materialList));
    }

    public void onApagar(){
        MaterialDAL dal = new MaterialDAL();
        if(table.getSelectionModel().getSelectedIndex() != -1){
            //Perguntar
           // Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Deseja apagar");
            if(!dal.apagar(table.getSelectionModel().getSelectedItem())){
                    Alert alert2 = new Alert(Alert.AlertType.ERROR,"Erro ao apagar o material");
                    alert2.setContentText(SingletonDB.getConexao().getMensagemErro());
                    alert2.show();
            }
            carregarTabela("");
        }
    }

    public void onAlterar() throws Exception{
        if(table.getSelectionModel().getSelectedIndex() != -1) {
            MaterialFormController.material = table.getSelectionModel().getSelectedItem();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("material/material-form-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Material");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            carregarTabela("");
        }
    }

    public void onNovoMaterial(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("material/material-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Material");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        MaterialFormController.material = null;
        stage.showAndWait();
        carregarTabela("");
    }

    public void onFechar(ActionEvent actionEvent) {
        TextField.getScene().getWindow().hide();
    }
}
