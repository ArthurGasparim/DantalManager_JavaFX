package unoeste.fipp.dentalfx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unoeste.fipp.dentalfx.db.dals.MaterialDAL;
import unoeste.fipp.dentalfx.db.dals.PessoaDal;
import unoeste.fipp.dentalfx.db.entidades.Dentista;
import unoeste.fipp.dentalfx.db.entidades.Material;
import unoeste.fipp.dentalfx.db.entidades.Pessoa;
import unoeste.fipp.dentalfx.db.util.SingletonDB;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DentistaTableController implements Initializable {

    @FXML
    private TextField TextField;

    @FXML
    private TableColumn<Dentista, String> colEmail;

    @FXML
    private TableColumn<Dentista, String> colFone;

    @FXML
    private TableColumn<Dentista, Integer> colcro;

    @FXML
    private TableColumn<Dentista, Integer> colid;

    @FXML
    private TableColumn<Dentista, String> colnome;

    @FXML
    private TableView<Pessoa> table;

    @FXML
    void onAlterar(ActionEvent event) throws Exception{
        if(table.getSelectionModel().getSelectedIndex() != -1) {
            DentistaFormController.dentista =(Dentista) table.getSelectionModel().getSelectedItem();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("dentista/dentista-form-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Dentista");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            carregarTabela("");
        }
    }

    @FXML
    void onApagar(ActionEvent event) {
        PessoaDal dal = new PessoaDal();
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

    @FXML
    void onBusca(KeyEvent event) {
        carregarTabela("den_nome LIKE '%"+TextField.getText()+"%'");
    }

    @FXML
    void onFechar(ActionEvent event) {
        TextField.getScene().getWindow().hide();
    }

    @FXML
    void onNovoMaterial(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("dentista/dentissta-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dentista");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        DentistaFormController.dentista = null;
        stage.showAndWait();
        carregarTabela("");
    }

    private void carregarTabela(String filtro) {
        PessoaDal dal = new PessoaDal();
        List<Pessoa> dentistaList = dal.get(filtro,new Dentista());
        table.setItems(FXCollections.observableArrayList(dentistaList));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colnome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colcro.setCellValueFactory(new PropertyValueFactory<>("cro"));
        colFone.setCellValueFactory(new PropertyValueFactory<>("fone"));
        carregarTabela("");
    }
}
