package eu.donals;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;


/*
* https://www.baeldung.com/java-pdf-creation
* https://www.vogella.com/tutorials/JavaPDF/article.html
*
*
* <dependency>
            <groupId>com.itextpdf</groupId>
            <artifactId>itextpdf</artifactId>
            <version>5.5.13</version>
   </dependency>
* */


public class PDFGenerator {

    private static String FILE = "/home/daniel/Projects/vu_text_adventure/src/main/java/eu/donals/StinkyMonkeyCertificate.pdf";

    public static void generatePDF() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();

            addMetaData(document);
            addContent(document);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("StinkyMonkeyCertificate");
        document.addSubject("Software Design VU - 2020");
        document.addCreator("Donal, Daniel, Nariman and Sofia");
    }

    private static void addContent(Document document) throws DocumentException, IOException {

        GameState gameState = GameState.getInstance();

        document.add(new Paragraph(
                "Congratulations!\n" +
                "You have completed the Stinky Monkey game! Here are some stats:"));

        String playerName = gameState.getPlayer().getName();
        String playerInv = gameState.getPlayer().getInventory().toString();

        List list = new List();
        list.add("Name: " + playerName);
        list.add("Final inventory: " + playerInv);
        document.add(list);

        String imageFile = "src/main/java/eu/donals/certificate-stamp.png";
        Image image = Image.getInstance(imageFile);
        document.add(image);
    }
}
