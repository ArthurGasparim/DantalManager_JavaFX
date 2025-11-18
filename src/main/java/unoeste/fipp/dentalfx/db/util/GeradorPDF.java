package unoeste.fipp.dentalfx.db.util;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import unoeste.fipp.dentalfx.db.entidades.Agenda;
import unoeste.fipp.dentalfx.db.entidades.Paciente;
import unoeste.fipp.dentalfx.db.entidades.Horario;
import unoeste.fipp.dentalfx.db.dals.AgendaDAL;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GeradorPDF {

    // Método auxiliar para garantir que a string não seja null
    private static String safeString(String str) {
        return str != null && !str.trim().isEmpty() ? str : "N/A";
    }

    public static void gerarFichaPaciente(Paciente paciente) {
        String dest = "Relatorio_Paciente_" + safeString(paciente.getNome()).replaceAll("[^a-zA-Z0-9_-]", "") + ".pdf";

        List<Agenda> minhasConsultas = new ArrayList<>();
        AgendaDAL agendaDAO = new AgendaDAL();

        try (PdfWriter writer = new PdfWriter(dest);
             PdfDocument pdf = new PdfDocument(writer);
             Document doc = new Document(pdf)) {

            // 1. Configurações de Fontes e Estilos
            PdfFont fonteTitulo = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            Border cellBorder = new SolidBorder(Color.BLACK, 1);
            PdfFont headerFont = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);

            // Título Principal
            Text titulo = new Text("RELATÓRIO MÉDICO INDIVIDUAL");
            titulo.setFont(fonteTitulo).setFontSize(16);
            Paragraph paragraph = new Paragraph(titulo).setTextAlignment(TextAlignment.CENTER);
            doc.add(paragraph);
            doc.add(new Paragraph("\n")); // Espaço

            // 2. Tabela de Dados do Paciente (2 Colunas)
            float[] patientColumnWidths = {120F, 405F};
            Table patientTable = new Table(patientColumnWidths);

            // Adicionando células na tabela com tratamento de nulos
            patientTable.addCell(new Cell().add(new Paragraph("Nome do paciente:").setFont(fonteTitulo)).setBorder(cellBorder));
            patientTable.addCell(new Cell().add(safeString(paciente.getNome())).setBorder(cellBorder));

            patientTable.addCell(new Cell().add(new Paragraph("CPF:").setFont(fonteTitulo)).setBorder(cellBorder));
            patientTable.addCell(new Cell().add(safeString(paciente.getCpf())).setBorder(cellBorder));

            patientTable.addCell(new Cell().add(new Paragraph("CEP:").setFont(fonteTitulo)).setBorder(cellBorder));
            patientTable.addCell(new Cell().add(safeString(paciente.getCep())).setBorder(cellBorder));

            String endereco = safeString(paciente.getRua()) + ", " + safeString(paciente.getNumero()) + " - " + safeString(paciente.getCidade()) + ", " + safeString(paciente.getUf());
            patientTable.addCell(new Cell().add(new Paragraph("Endereço:").setFont(fonteTitulo)).setBorder(cellBorder));
            patientTable.addCell(new Cell().add(endereco).setBorder(cellBorder));

            patientTable.addCell(new Cell().add(new Paragraph("Telefone:").setFont(fonteTitulo)).setBorder(cellBorder));
            patientTable.addCell(new Cell().add(safeString(paciente.getFone())).setBorder(cellBorder));

            patientTable.addCell(new Cell().add(new Paragraph("Email:").setFont(fonteTitulo)).setBorder(cellBorder));
            patientTable.addCell(new Cell().add(safeString(paciente.getEmail())).setBorder(cellBorder));

            // ESTA TABELA DEVE SEMPRE RENDERIZAR. Se o PDF estiver vazio, verifique o objeto 'paciente'.
            doc.add(patientTable);
            doc.add(new Paragraph("\n"));

            // 3. Carregar e Processar Consultas
            minhasConsultas = agendaDAO.carregaAgendaPaciente(paciente);
            for(Agenda a : minhasConsultas){
                System.out.println(a);
            }
            if (minhasConsultas != null && !minhasConsultas.isEmpty()) {

                minhasConsultas.sort(Comparator.comparing((Agenda agenda) -> safeString(agenda.getDentista().getNome()))
                        .thenComparing(Agenda::getData));

                // Configuração das larguras para as tabelas de consultas
                float[] effectedColWidths = {100F, 100F, 80F, 235F}; // 4 colunas (Dentista, Data, Horário, Relato)
                float[] scheduledColWidths = {150F, 180F, 185F};     // 3 colunas (Dentista, Data, Horário)

                Table effectedAppointmentsTable = new Table(effectedColWidths);
                Table scheduledAppointmentsTable = new Table(scheduledColWidths);

                // --- Cabeçalhos da Tabela de Consultas EFETIVADAS ---
                effectedAppointmentsTable.addHeaderCell(new Cell().add("Dentista").setFont(headerFont).setBackgroundColor(Color.LIGHT_GRAY).setBorder(cellBorder).setTextAlignment(TextAlignment.CENTER));
                effectedAppointmentsTable.addHeaderCell(new Cell().add("Data").setFont(headerFont).setBackgroundColor(Color.LIGHT_GRAY).setBorder(cellBorder).setTextAlignment(TextAlignment.CENTER));
                effectedAppointmentsTable.addHeaderCell(new Cell().add("Horário").setFont(headerFont).setBackgroundColor(Color.LIGHT_GRAY).setBorder(cellBorder).setTextAlignment(TextAlignment.CENTER));
                effectedAppointmentsTable.addHeaderCell(new Cell().add("Relato do Atendimento").setFont(headerFont).setBackgroundColor(Color.LIGHT_GRAY).setBorder(cellBorder).setTextAlignment(TextAlignment.CENTER));

                // --- Cabeçalhos da Tabela de Consultas AGENDADAS ---
                scheduledAppointmentsTable.addHeaderCell(new Cell().add("Dentista").setFont(headerFont).setBackgroundColor(Color.LIGHT_GRAY).setBorder(cellBorder).setTextAlignment(TextAlignment.CENTER));
                scheduledAppointmentsTable.addHeaderCell(new Cell().add("Data").setFont(headerFont).setBackgroundColor(Color.LIGHT_GRAY).setBorder(cellBorder).setTextAlignment(TextAlignment.CENTER));
                scheduledAppointmentsTable.addHeaderCell(new Cell().add("Horário").setFont(headerFont).setBackgroundColor(Color.LIGHT_GRAY).setBorder(cellBorder).setTextAlignment(TextAlignment.CENTER));

                boolean hasEffected = false;
                boolean hasScheduled = false;

                for (Agenda a : minhasConsultas) {
                    // ATENÇÃO: É ESSENCIAL que a.getDentista() e a.getHorarioList() não sejam nulos ou vazios
                    if (a.getHorarioList() == null) continue;

                    String dentistaNome = a.getDentista() != null ? safeString(a.getDentista().getNome()) : "N/A";
                    String data = a.getData() != null ? a.getData().toString() : "N/A";

                    for (Horario h : a.getHorarioList()) {

                        // Garante que o horário pertence a um paciente e não está vazio
                        if (h.getPaciente() == null) continue;

                        String hora = String.valueOf(h.getSequencia());

                        if (h.getAtendimento() != null) {
                            // Consulta Efetuada (com Relato)
                            String relato = safeString(h.getAtendimento().getRelato());

                            effectedAppointmentsTable.addCell(new Cell().add(dentistaNome).setBorder(cellBorder));
                            effectedAppointmentsTable.addCell(new Cell().add(data).setBorder(cellBorder));
                            effectedAppointmentsTable.addCell(new Cell().add(hora).setBorder(cellBorder));
                            effectedAppointmentsTable.addCell(new Cell().add(relato).setBorder(cellBorder));

                            hasEffected = true;
                        } else {
                            // Consulta Agendada (sem Relato)
                            scheduledAppointmentsTable.addCell(new Cell().add(dentistaNome).setBorder(cellBorder));
                            scheduledAppointmentsTable.addCell(new Cell().add(data).setBorder(cellBorder));
                            scheduledAppointmentsTable.addCell(new Cell().add(hora).setBorder(cellBorder));

                            hasScheduled = true;
                        }
                    }
                }

                // 4. Adicionar Tabelas ao Documento
                if (hasEffected) {
                    doc.add(new Paragraph("CONSULTAS EFETIVADAS").setFont(fonteTitulo).setFontSize(14).setTextAlignment(TextAlignment.CENTER));
                    doc.add(effectedAppointmentsTable);
                    doc.add(new Paragraph("\n"));
                }
                if (hasScheduled) {
                    doc.add(new Paragraph("CONSULTAS AGENDADAS").setFont(fonteTitulo).setFontSize(14).setTextAlignment(TextAlignment.CENTER));
                    doc.add(scheduledAppointmentsTable);
                    doc.add(new Paragraph("\n"));
                }

            } else {
                // Nenhuma consulta encontrada
                doc.add(new Paragraph("ESTE PACIENTE AINDA NÃO POSSUI NENHUMA CONSULTA").setFont(fonteTitulo).setFontSize(16).setTextAlignment(TextAlignment.CENTER));
            }
            doc.close();
            System.out.println("documento criado..");

            //abrindo e mostrando o PDF
            Desktop.getDesktop().open(new File(dest));


        } catch (Exception e) {
            // Lança um novo RuntimeException mais claro para o desenvolvedor
            throw new RuntimeException("Erro ao gerar PDF para o paciente " + safeString(paciente.getNome()) + ". Por favor, verifique a estrutura do banco de dados e as entidades (Agenda, Dentista, etc.).", e);
        }
    }
}