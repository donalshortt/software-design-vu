package eu.donals;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.simple.parser.ParseException;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFGenerator {

    private static String fileLocation = "./certificate.pdf";

    private static void addMetaData(Document document) {
        document.addTitle("Certificate");
    }

    private static void addContent(Document document) throws DocumentException, IOException, ParseException {

        GameState gameState = GameState.getInstance();

        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.BOLD, BaseColor.BLACK);
        Font textFont = new Font(Font.FontFamily.TIMES_ROMAN, 10.0f, Font.NORMAL, BaseColor.BLACK);

        Chunk a = new Chunk("Congratulations!\n", titleFont);

        Chunk b = new Chunk("You have completed the " + (String) Loader.parseMetaData().get("title")
                + "\n\nMade by: " + (String) Loader.parseMetaData().get("team")
                + "\nGame statistics:\n", textFont);

        Paragraph title = new Paragraph(a);
        Paragraph text = new Paragraph(b);

        document.add(title);
        document.add(text);

        String playerName = gameState.getPlayer().getName();
        String playerInv = gameState.getPlayer().getInventory().toString();

        List list = new List();
        list.add("Name: " + playerName);
        list.add("Final inventory: " + playerInv);
        document.add(list);

        String imageFile = "src/main/resources/stamp.jpg";
        Image image = Image.getInstance(imageFile);
        image.setAbsolutePosition(450, 200);
        document.add(image);
    }

    public static void generatePDF() {
        try {
            Document document = new Document();
            document.setPageSize(PageSize.A4.rotate());

            PdfWriter.getInstance(document, new FileOutputStream(fileLocation));
            document.open();

            addMetaData(document);
            addContent(document);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFileLocation() { return fileLocation; }
}
