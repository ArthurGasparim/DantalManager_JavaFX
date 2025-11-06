package unoeste.fipp.dentalfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;
import unoeste.fipp.dentalfx.db.util.SingletonDB;
import unoeste.fipp.dentalfx.utils.Seguranca;

import java.io.File;
import java.sql.ResultSet;

public class MenuController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onDentista(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("dentista/dentista-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dentista");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onMaterial(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("material/material-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Material");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onProcedimento(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("procedimento/procedimento-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Procedimento");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onPaciente(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("paciente/paciente-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Paciente");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onUsuario(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("Usuario/usuario-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Usuario");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onAgendamento(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("agendamentoSecre-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Agendamento");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void onRelAtendimento(ActionEvent event) {

    }

    @FXML
    void onRelDentista(ActionEvent event) {

    }

    @FXML
    void onRelMaterial(ActionEvent event) {
        gerarRelatorio("select * from material ORDER BY mat_desc","MyReports/rel_Materiais.jasper","Relatório de Materiais");


    }

    private void gerarRelatorio(String sql,String relat, String titulo) {
        try {
            //sql para obter os dados para o relatorio
            ResultSet rs = SingletonDB.getConexao().consultar(sql);
            //implementação da interface JRDataSource para DataSource ResultSet
            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
            //chamando o relatório
            String jasperPrint = JasperFillManager.fillReportToFile(relat,null, jrRS);
            JasperViewer viewer = new JasperViewer(jasperPrint, false, false);
            viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);//maximizado
            viewer.setTitle(titulo);//titulo do relatório
            viewer.setVisible(true);
        }
        catch (Exception erro) {
            erro.printStackTrace(); }
    }

    @FXML
    void onRelPaciente(ActionEvent event) {

    }

    @FXML
    void onRelProcedimento(ActionEvent event) {

    }

    public void onCriarUsuario(ActionEvent actionEvent) {
    }

    public void onBackUp(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("sql file","*.sql"));
        File bkp = fileChooser.showSaveDialog(null);
        if(bkp != null){
            try {

                Seguranca.backup(bkp.getAbsolutePath(), "sisdentaldb");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void onRestore(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("sql file","*.sql"));
        File bkp = fileChooser.showOpenDialog(null);
        if(bkp != null){
            try {

                Seguranca.restaurar(bkp.getAbsolutePath(), "sisdentaldb");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}