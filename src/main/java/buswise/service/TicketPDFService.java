package buswise.service;

import buswise.dao.BookingDao;
import buswise.model.Bookings;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.io.font.constants.StandardFonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class TicketPDFService {

    @Autowired
    private BookingDao bookingDao;

    private PdfFont boldFont;

    public String createPdf(HttpServletRequest request, int bookingId) {
        HttpSession httpSession = request.getSession();
        String filePath = httpSession.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "resources"
                + File.separator + "tickets" + File.separator + "Ticket_"+bookingId+".pdf";

        try {
            List<Bookings> bookings = bookingDao.getBookingById(bookingId);
            Bookings bookings1 = bookings.get(0);
            List<String> names = bookingDao.getNamesByBookingId(bookingId);
            List<String> seats = bookingDao.getSeatsByBookingId(bookingId);
            List<String> gender = bookingDao.getGenderByBookingId(bookingId);
            List<String> age = bookingDao.getAgeByBookingId(bookingId);

            PdfWriter writer = new PdfWriter(filePath);

            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.setDefaultPageSize(PageSize.A4);

            Document document = new Document(pdfDoc);


            boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            document.setMargins(50, 50, 50, 50);

            addHeader(document);

            Table passengerTable = createTableWithHeader("Passenger Information");
            addTableData(passengerTable, "Name:", names.toString().substring(1,  names.toString().length() - 1));
            addTableData(passengerTable, "Age:",   age.toString().substring(1,  age.toString().length() - 1));
            addTableData(passengerTable, "Gender:", gender.toString().substring(1,  gender.toString().length() - 1));
            addTableData(passengerTable, "Phone:", bookings1.getPhone());
            addTableData(passengerTable, "Email:", bookings1.getEmail());

            Table journeyTable = createTableWithHeader("Journey Details");
            addTableData(journeyTable, "Departure:", bookings1.getSelectedSource()+","+ bookings1.getScheduleId().getTripDate() +" "+ bookings1.getDepatureTime()  );
            addTableData(journeyTable, "Arrival:", bookings1.getSelectedDestination());
            addTableData(journeyTable, "Bus Number:", bookings1.getScheduleId().getBusId().getBusNumber());
            addTableData(journeyTable, "Seat Number(s):", seats.toString().substring(1,  seats.toString().length() - 1), ColorConstants.RED);
            addTableData(journeyTable, "Route:", bookings1.getScheduleId().getRouteId().getSource() +" to " + bookings1.getScheduleId().getRouteId().getDestination());
            addTableData(journeyTable, "Total Fare:", String.valueOf(bookings1.getTotalAmount()));
            document.add(passengerTable);
            document.add(journeyTable);

            addFooter(document);

            document.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }

    private void addHeader(Document document) {
        Paragraph header = new Paragraph("BusWise\n")
                .setFont(boldFont)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
        document.add(header);

        Paragraph contact = new Paragraph("Contact: +1234567890 | Email: info@buswise.com")
                .setFont(boldFont)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(contact);
    }

    private Table createTableWithHeader(String headerText) {
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
        table.setWidth(UnitValue.createPercentValue(100)); // Full page width
        table.setMarginBottom(20);
        table.addHeaderCell(new Cell(1, 2)
                .add(new Paragraph(headerText).setFont(boldFont).setBackgroundColor(ColorConstants.LIGHT_GRAY).setTextAlignment(TextAlignment.CENTER)));
        return table;
    }

    private void addTableData(Table table, String label, String value) {
        table.addCell(new Cell().add(new Paragraph(label).setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph(value)));
    }

    private void addTableData(Table table, String label, String value, com.itextpdf.kernel.colors.Color color) {
        table.addCell(new Cell().add(new Paragraph(label).setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph(value).setFontColor(color)));
    }

    private void addFooter(Document document) {
        Paragraph footer = new Paragraph("Thank you for choosing BusWise Travels!\nTerms and Conditions apply. Please carry a valid ID proof during the journey.")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12)
                .setMarginTop(20);
        document.add(footer);
    }
}
