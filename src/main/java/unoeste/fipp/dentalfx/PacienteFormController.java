package unoeste.fipp.dentalfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.json.JSONObject;
import unoeste.fipp.dentalfx.db.entidades.Paciente;
import unoeste.fipp.dentalfx.utils.MaskFieldUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.net.URLConnection;

public class PacienteFormController implements Initializable {
    public static Paciente paciente;
    @FXML
    private TextField tfCEP;

    @FXML
    private TextField tfCPF;

    @FXML
    private TextField tfCidade;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfRua;

    @FXML
    void onCancelar(ActionEvent event) {
        tfCEP.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        tfCEP.getScene().getWindow().hide();
    }

    public void onBuscarCep(KeyEvent keyEvent) {
        //Criar Thread
        if(tfCEP.getText().length() == 9){
            String json = consultaCep(tfCEP.getText(),"json");
            JSONObject jsonObject = new JSONObject(json);
            tfRua.setText(jsonObject.getString("logradouro"));
            tfCidade.setText(jsonObject.getString("localidade"));

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MaskFieldUtil.cepField(tfCEP);
        MaskFieldUtil.cpfField(tfCPF);
    }

    public String consultaCep(String cep, String formato)
    {
        StringBuffer dados = new StringBuffer();
        try {
            URL url = new URL("https://viacep.com.br/ws/"+ cep + "/"+formato+"/");
            URLConnection con = url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setAllowUserInteraction(false);
            InputStream in = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s = "";
            while (null != (s = br.readLine()))
                dados.append(s);
            br.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dados.toString();
    }
}
