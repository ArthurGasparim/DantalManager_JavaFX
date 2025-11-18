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
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import unoeste.fipp.dentalfx.db.dals.PessoaDal;
import unoeste.fipp.dentalfx.db.entidades.Paciente;
import unoeste.fipp.dentalfx.db.entidades.Pessoa;
import unoeste.fipp.dentalfx.db.util.GeradorPDF;
import unoeste.fipp.dentalfx.db.util.SingletonDB;
import unoeste.fipp.dentalfx.utils.Seguranca;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    @FXML
    void onRelAtendimento(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("filtro-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Filtros");
        stage.setScene(scene);
        stage.showAndWait();
        gerarRelatorio("SELECT c.con_id, c.con_data, c.con_horario, c.con_relato, p.pac_nome, d.den_nome, d.den_cro FROM consulta c JOIN public.paciente p ON c.pac_id = p.pac_id JOIN public.dentista d ON c.den_id = d.den_id WHERE c.con_efetivado = true AND c.con_data >= '"+FiltroView.dateIni+"' AND c.con_data <= '"+FiltroView.dateFim+"' AND c.den_id = "+FiltroView.id+" ORDER BY d.den_nome, c.con_data, c.con_horario","MyReports/rel_atendimentos.jasper","Relatório de Atendimentos");
    }

    @FXML
    void onRelDentista(ActionEvent event) {
        gerarRelatorio("SELECT * FROM dentista ORDER BY den_nome","MyReports/res_dentista.jasper","Relatório de Dentista");
    }

    @FXML
    void onRelMaterial(ActionEvent event) {
        gerarRelatorio("select * from material ORDER BY mat_desc","MyReports/rel_Materiais.jasper","Relatório de Materiais");


    }

    public void gerarRelatorio(String sql, String relat, String titulo, Map<String, Object> parametros) {
        ResultSet rs = null;

        try {

            rs = SingletonDB.getConexao().consultar(sql);


            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

            Map<String, Object> reportParameters = (parametros != null) ? parametros : new HashMap<>();

            JasperPrint print = JasperFillManager.fillReport(relat, reportParameters, jrRS);

            JasperViewer viewer = new JasperViewer(print, false);
            viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            viewer.setTitle(titulo);
            viewer.setVisible(true);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
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
        gerarRelatorio("SELECT * FROM paciente ORDER BY pac_cidade, pac_nome","MyReports/rel_pacientes.jasper","Relatório de Pacientes");
    }

    @FXML
    void onRelProcedimento(ActionEvent event) {
        gerarRelatorio("SELECT * FROM procedimento ORDER BY pro_desc","MyReports/rel_procedimentos.jasper","Relatório de Procedimentos");
    }

    public void onCriarUsuario(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("Usuario/usuario-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Usuario");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        UsuarioFormController.usuario = null;
        stage.showAndWait();
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

    public void onSobre(ActionEvent actionEvent) throws Exception{
        HelpController.path = "sobre.html";
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("help-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Ajuda do DentalFX");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onTopicos(ActionEvent actionEvent) throws Exception{
       // File doc = new File("ajuda/main.html");
        //Desktop.getDesktop().browse(doc.toURI());
        HelpController.path = "main.html";
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DentalFX.class.getResource("help-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Ajuda do DentalFX");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onRelPacientePDF(ActionEvent actionEvent) {
        int id = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite o ID do paciente do relatório"));
        PessoaDal pessoaDal =new PessoaDal();
        Paciente paciente =(Paciente) pessoaDal.get(id,new Paciente());
        GeradorPDF.gerarFichaPaciente(paciente);
    }
}