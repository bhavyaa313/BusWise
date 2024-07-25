package buswise.service;

import buswise.dao.BookingDao;
import buswise.model.Bookings;
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
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DailySalesPdfService {

    @Autowired
    private BookingDao bookingDao;

    private PdfFont boldFont;

    public String createDailySalesPdf(HttpServletRequest request, String dateString) {
        String filePath = request.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "resources"
                + File.separator + "reports" + File.separator + "DailySalesReport_" + dateString + ".pdf";

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            List<Bookings> bookings = bookingDao.getDailySalesWhole(localDate);
            PdfWriter writer = new PdfWriter(filePath);

            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.setDefaultPageSize(PageSize.A4);

            Document document = new Document(pdfDoc);

            boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            document.setMargins(50, 50, 50, 50);
            addHeader(document);
            double totalSales = bookings.stream().mapToDouble(Bookings::getTotalAmount).sum();
            int numberOfBookings = bookings.size();
            addSalesSummary(document, totalSales, numberOfBookings , dateString);
            addDetailedBookings(document, bookings);
            addFooter(document);
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        String pathTemp = String.format("/%s/%s/", "resources", "reports");
        String path = request.getContextPath() + pathTemp + "DailySalesReport_" + dateString + ".pdf";
        return path;
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

    private void addSalesSummary(Document document, double totalSales, int numberOfBookings, String date) {
        Paragraph summary = new Paragraph("Daily Sales Summary\n" +  date)
                .setFont(boldFont)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
        document.add(summary);

        Table summaryTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
        summaryTable.setWidth(UnitValue.createPercentValue(100)); // Full page width
        summaryTable.setMarginBottom(20);
        addTableData(summaryTable, "Total Sales:", String.valueOf(totalSales));
        addTableData(summaryTable, "Number of Bookings:", String.valueOf(numberOfBookings));
        document.add(summaryTable);
    }

    private void addDetailedBookings(Document document, List<Bookings> bookings) {
        Paragraph details = new Paragraph("Detailed Booking Information\n")
                .setFont(boldFont)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
        document.add(details);

        Table bookingTable = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 1, 2}));
        bookingTable.setWidth(UnitValue.createPercentValue(100)); // Full page width
        bookingTable.setMarginBottom(20);

        bookingTable.addHeaderCell(new Cell().add(new Paragraph("Booking ID").setFont(boldFont)));
        bookingTable.addHeaderCell(new Cell().add(new Paragraph("Email").setFont(boldFont)));
        bookingTable.addHeaderCell(new Cell().add(new Paragraph("Route").setFont(boldFont)));
        bookingTable.addHeaderCell(new Cell().add(new Paragraph("Total Amount").setFont(boldFont)));
        bookingTable.addHeaderCell(new Cell().add(new Paragraph("Booking Date").setFont(boldFont)));


        for (Bookings booking : bookings) {
            bookingTable.addCell(new Cell().add(new Paragraph(String.valueOf(booking.getBookingId()))));
            bookingTable.addCell(new Cell().add(new Paragraph(booking.getEmail())));
            bookingTable.addCell(new Cell().add(new Paragraph(booking.getScheduleId().getRouteId().getSource() + " to " + booking.getScheduleId().getRouteId().getDestination())));
            bookingTable.addCell(new Cell().add(new Paragraph(String.valueOf(booking.getTotalAmount()))));
            bookingTable.addCell(new Cell().add(new Paragraph(String.valueOf(booking.getBookingDate().toLocalDate()))));
        }

        document.add(bookingTable);
    }

    private void addTableData(Table table, String label, String value) {
        table.addCell(new Cell().add(new Paragraph(label).setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph(value)));
    }

    private void addFooter(Document document) {
        Paragraph footer = new Paragraph("Thank you")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12)
                .setMarginTop(20);
        document.add(footer);
    }
}
