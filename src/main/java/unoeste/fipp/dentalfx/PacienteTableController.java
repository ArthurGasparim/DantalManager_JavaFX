package unoeste.fipp.dentalfx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unoeste.fipp.dentalfx.db.dals.MaterialDAL;
import unoeste.fipp.dentalfx.db.dals.PessoaDal;
import unoeste.fipp.dentalfx.db.entidades.Material;
import unoeste.fipp.dentalfx.db.entidades.Paciente;
import unoeste.fipp.dentalfx.db.entidades.Pessoa;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PacienteTableController implements Initializable {

    @FXML
    private TextField TextField;

    @FXML
    private TableColumn<Pessoa, String> colCidade;

    @FXML
    private TableColumn<Pessoa, String> colFone;

    @FXML
    private TableColumn<Pessoa, String> colNome;

    @FXML
    private TableColumn<Pessoa, Integer> colid;

    @FXML
    private TableView<Pessoa> table;

    @FXML
    void onAlterar(ActionEvent event) {

    }

    @FXML
    void onApagar(ActionEvent event) {

    }

    @FXML
    void onBusca(KeyEvent event) {

    }

    @FXML
    void onFechar(ActionEvent event) {

    }

    @FXML
    void onNovoMaterial(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("paciente/paciente-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Paciente");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        PacienteFormController.paciente = null;
        stage.showAndWait();
        carregarTabela("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colFone.setCellValueFactory(new PropertyValueFactory<>("fone"));
        carregarTabela("");
    }

    private void carregarTabela(String s) {
        PessoaDal dal = new PessoaDal();
        List<Pessoa> pessoaList = dal.get(s,new Paciente());
        table.setItems(FXCollections.observableArrayList(pessoaList));
    }
}
