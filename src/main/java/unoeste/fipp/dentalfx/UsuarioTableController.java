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
import unoeste.fipp.dentalfx.db.dals.PessoaDal;
import unoeste.fipp.dentalfx.db.entidades.Dentista;
import unoeste.fipp.dentalfx.db.entidades.Pessoa;
import unoeste.fipp.dentalfx.db.entidades.Usuario;
import unoeste.fipp.dentalfx.db.util.SingletonDB;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class UsuarioTableController implements Initializable {
    @FXML
    private TextField TextField;

    @FXML
    private TableColumn<Usuario, Integer> colNivel;

    @FXML
    private TableColumn<Usuario, String> colNome;

    @FXML
    private TableColumn<Usuario, Integer> colid;

    @FXML
    private TableView<Pessoa> table;

    @FXML
    void onAlterar(ActionEvent event) throws Exception{
        if(table.getSelectionModel().getSelectedIndex() != -1) {
            UsuarioFormController.usuario =(Usuario) table.getSelectionModel().getSelectedItem();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("Usuario/usuario-form-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Usuario");
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

    private void carregarTabela(String filtro) {
        PessoaDal dal = new PessoaDal();
        List<Pessoa> dentistaList = dal.get(filtro,new Usuario());
        System.out.println(dentistaList);
        table.setItems(FXCollections.observableArrayList(dentistaList));
    }

    @FXML
    void onBusca(KeyEvent event) {
        carregarTabela("uso_nome LIKE '%"+TextField.getText()+"%'");
    }

    @FXML
    void onFechar(ActionEvent event) {
        table.getScene().getWindow().hide();
    }

    @FXML
    void onNovoMaterial(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("Usuario/usuario-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Usuario");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        UsuarioFormController.usuario = null;
        stage.showAndWait();
        carregarTabela("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        carregarTabela("");
    }
}
