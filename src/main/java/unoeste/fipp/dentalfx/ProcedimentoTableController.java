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
import unoeste.fipp.dentalfx.db.dals.ProcedimentoDAL;
import unoeste.fipp.dentalfx.db.entidades.Procedimento;
import unoeste.fipp.dentalfx.db.util.SingletonDB;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProcedimentoTableController implements Initializable {

    public javafx.scene.control.TextField TextField;
    @FXML
    private TableColumn<Procedimento, String> colDesc;

    @FXML
    private TableColumn<Procedimento, Integer> colId;

    @FXML
    private TableColumn<Procedimento, Double> colTempo;

    @FXML
    private TableColumn<Procedimento, Double> colValor;

    @FXML
    private TableView<Procedimento> table;

    public static Procedimento procedimento = null;
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colTempo.setCellValueFactory(new PropertyValueFactory<>("tempo"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        carregarTabela("");
    }

    private void carregarTabela(String filtro) {
        ProcedimentoDAL dal=new ProcedimentoDAL();
        List<Procedimento> produtoList = dal.get(filtro);
        table.setItems(FXCollections.observableArrayList(produtoList));
    }


    public void onNovoMaterial(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("procedimento/procedimento-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Material");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        ProcedimentoFormController.procedimento = null;
        stage.showAndWait();
        carregarTabela("");
    }

    public void onApagar(ActionEvent actionEvent) {
        ProcedimentoDAL dal = new ProcedimentoDAL();

        if(table.getSelectionModel().getSelectedIndex() != -1) {
            if(!dal.apagar(table.getSelectionModel().getSelectedItem())){
                Alert alert2 = new Alert(Alert.AlertType.ERROR,"Erro ao apagar o material");
                alert2.setContentText(SingletonDB.getConexao().getMensagemErro());
                alert2.show();
            }
            carregarTabela("");

        }
    }

    public void onAlterar(ActionEvent actionEvent) throws Exception{
        if(table.getSelectionModel().getSelectedIndex() != -1) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("procedimento/procedimento-form-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Procedimento");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            ProcedimentoFormController.procedimento = table.getSelectionModel().getSelectedItem();
            stage.showAndWait();
            carregarTabela("");
        }
    }

    public void onFechar(ActionEvent actionEvent) {
        TextField.getScene().getWindow().hide();
    }


    public void onBuscar(KeyEvent keyEvent) {
        carregarTabela("pro_desc LIKE '%"+TextField.getText()+"%'");
    }
}
