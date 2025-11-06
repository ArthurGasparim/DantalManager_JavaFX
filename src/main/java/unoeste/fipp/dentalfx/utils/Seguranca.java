package unoeste.fipp.dentalfx.utils;

import javafx.scene.control.Alert;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Seguranca {

    public static boolean backup(String arquivo, String database) throws Exception
    {
        final ArrayList<String> comandos = new ArrayList();
        comandos.add("bdutil/pg_dump.exe"); comandos.add("--host");
        comandos.add("localhost"); //ou  comandos.add("192.168.0.1");
        comandos.add("--port"); comandos.add("5432"); comandos.add("--username");
        comandos.add("postgres");comandos.add("--format");comandos.add("custom");
        comandos.add("--blobs");comandos.add("--verbose");comandos.add("--file");
        comandos.add(arquivo);
        comandos.add(database);
        ProcessBuilder pb = new ProcessBuilder(comandos);
        pb.environment().put("PGPASSWORD", "postgres123");
        String lines = "";
        try {
            final Process process = pb.start();
            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                lines += line +"\n";
                System.err.println(line); line = r.readLine();
            }
            r.close();
            process.waitFor();
            process.destroy();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Backup realizado com sucesso");
            alert.setContentText(lines);
            alert.showAndWait();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro na realização do backup.");
            return false;
        }
    }

    public static boolean restaurar(String arquivo, String database) throws Exception
    {
        final ArrayList<String> comandos = new ArrayList();
        comandos.add("bdutil/pg_restore.exe"); comandos.add("-c");
        comandos.add("--host"); comandos.add("localhost");
        comandos.add("--port"); comandos.add("5432");
        comandos.add("--username"); comandos.add("postgres");
        comandos.add("--dbname"); comandos.add(database);
        comandos.add("--verbose");
        comandos.add(arquivo);
        ProcessBuilder pb = new ProcessBuilder(comandos);
        pb.environment().put("PGPASSWORD", "postgres123");
        try {
            final Process process = pb.start();
            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                System.err.println(line);
                line = r.readLine();
            }
            r.close();
            process.waitFor();
            process.destroy();
            JOptionPane.showMessageDialog(null,"Restauração com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro na restauração.");
            return false;
        }
    }



}
