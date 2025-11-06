package unoeste.fipp.dentalfx.utils;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;

import java.awt.*;
import java.io.File;

public class FichaPaciente {
    public static boolean gerarPDF(String pdfFile){
        String dest = "D:/mypdf.pdf";
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(pdfFile);


            // Criando o documento  Pdf
            PdfDocument pdf = new PdfDocument(writer);

            // Criando o Document
            Document doc = new Document(pdf);

            //Definindo uma fonte grande
            PdfFont fonteTitulo = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            //Definindo um texto
            Text titulo = new Text("Nome da Empresa");
            titulo.setFont(fonteTitulo);
            titulo.setFontSize(48);
            //Definindo um parágrafo
            Paragraph paragraph = new Paragraph(titulo);
            //Adicionando o parágrafo
            doc.add(paragraph);

            // Criando a tabela com 3 colunas
            float[] pointColumnWidths = {150F, 150F, 150F};
            Table table = new Table(pointColumnWidths);

            // Adicionando celulas na tabela
            table.addCell(new Cell().add("Nome").setBackgroundColor(Color.LIGHT_GRAY));
            table.addCell(new Cell().add("Idade").setBackgroundColor(Color.LIGHT_GRAY));
            table.addCell(new Cell().add("Profissão").setBackgroundColor(Color.LIGHT_GRAY));
            table.addCell(new Cell().add("Jeniffer da Silva"));
            table.addCell(new Cell().add("28"));
            table.addCell(new Cell().add("Programadora"));
            table.addCell(new Cell().add("Samanta Barros"));
            table.addCell(new Cell().add("21"));
            table.addCell(new Cell().add("Médica"));

            // Adicionando a tabela no documento
            doc.add(table);

            // Fechando o documento
            doc.close();
            System.out.println("documento criado..");

            //abrindo e mostrando o PDF
            Desktop.getDesktop().open(new File(pdfFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static void  abrirPDF(String pdfFile){
        try {
            Desktop.getDesktop().open(new File(pdfFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
