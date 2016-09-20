package com.spring.elasticsearch.manager;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.spring.elasticsearch.model.domain.ExamResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Service
public class GeneratePdf {

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font answer = new Font(Font.FontFamily.TIMES_ROMAN, 7,
            Font.NORMAL);
    private static Font question = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.ITALIC);

    public GeneratePdf(){}

    public  static void doPdf(List<ExamResponse> examen, HttpServletResponse response){
        try {
            Document document = new Document();
            response.setContentType("application/pdf");
            PdfWriter.getInstance(document, response.getOutputStream());

            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("Examen.pdf"));
            document.open();

            addTitlePage(document);
            addContent(document,examen);

            document.close();
        }catch (DocumentException ex){
            System.out.print("ERROR:"+ex.fillInStackTrace());
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        preface.setSpacingAfter(1);
        preface.add(new Paragraph("Examen", catFont));

        preface.setSpacingBefore(2);
        //preface.add(new Paragraph("Fecha de generación: " + new Date(), catFont));
        document.add(preface);
       //document.newPage();
    }

    private static void addContent(Document document,List<ExamResponse> examen) throws DocumentException {
       for(ExamResponse ex:examen) {
            Paragraph para = new Paragraph(ex.getQuestion(), question);
            para.setAlignment(Element.ALIGN_LEFT);
            para.setSpacingBefore(2);
            para.setSpacingAfter(2);
            document.add(para);
          /*  com.itextpdf.text.List list =  new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);*/
            for(String a:ex.getAnswers()) {
             /*   ListItem i=new ListItem(a,answer);
                list.add(i);
                document.add(list);*/
                Paragraph para2 = new Paragraph(a, question);
                para2.setAlignment(Element.ALIGN_LEFT);
                para2.setIndentationLeft(50);
                para2.setIndentationRight(50);
                para2.setSpacingBefore(10);
                para2.setSpacingAfter(10);
                document.add(para2);
            }
         }

    }

}
