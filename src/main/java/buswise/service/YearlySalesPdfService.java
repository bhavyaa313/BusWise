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
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class YearlySalesPdfService {

    @Autowired
    private BookingDao bookingDao;

    private PdfFont boldFont;

    public String createYearlySalesPdf(HttpServletRequest request, int year) {
        String filePath = request.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "resources"
                + File.separator + "reports" + File.separator + "YearlySalesReport_" + year + ".pdf";

        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdfDoc);

            boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            document.setMargins(50, 50, 50, 50);

            addHeader(document, year);

            double totalSalesYearly = 0.0;
            int totalBookingsYearly = 0;

            for (int month = 1; month <= 12; month++) {
                LocalDate date = LocalDate.of(year, month, 1);
                LocalDateTime startOfMonth = date.atStartOfDay();
                LocalDateTime endOfMonth = date.withDayOfMonth(date.lengthOfMonth()).atTime(23, 59, 59);

                List<Bookings> bookings = bookingDao.getMonthlyBookings(date);

                if (!bookings.isEmpty()) {
                    addMonthlySales(document, date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), bookings);
                    double totalSalesMonth = bookings.stream().mapToDouble(Bookings::getTotalAmount).sum();
                    int totalBookingsMonth = bookings.size();
                    totalSalesYearly += totalSalesMonth;
                    totalBookingsYearly += totalBookingsMonth;
                }
            }

            addYearlySummary(document, totalSalesYearly, totalBookingsYearly);

            addFooter(document);

            document.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }

    private void addHeader(Document document, int year) {

        Paragraph header = new Paragraph("BusWise - Yearly Sales Report " + year + "\n")
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

    private void addMonthlySales(Document document, String monthName, List<Bookings> bookings) {
        Paragraph summary = new Paragraph(monthName + " Sales Summary\n")
                .setFont(boldFont)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
        document.add(summary);


        double totalSales = 0.0;
        for (Bookings booking : bookings) {
            totalSales += booking.getTotalAmount();
        }
        int numberOfBookings = bookings.size();

        Table summaryTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
        summaryTable.setWidth(UnitValue.createPercentValue(100)); // Full page width
        summaryTable.setMarginBottom(20);

        addTableData(summaryTable, "Total Sales:", String.valueOf(totalSales));
        addTableData(summaryTable, "Number of Bookings:", String.valueOf(numberOfBookings));

        document.add(summaryTable);

        addDetailedBookings(document, bookings);
    }

    private void addDetailedBookings(Document document, List<Bookings> bookings) {
        Paragraph details = new Paragraph("Detailed Booking Information\n")
                .setFont(boldFont)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
        document.add(details);

        Table bookingTable = new Table(UnitValue.createPercentArray(new float[]{1, 2, 1, 1}));
        bookingTable.setWidth(UnitValue.createPercentValue(100)); // Full page width
        bookingTable.setMarginBottom(20);
        bookingTable.addHeaderCell(new Cell().add(new Paragraph("Booking ID").setFont(boldFont)));
        bookingTable.addHeaderCell(new Cell().add(new Paragraph("User").setFont(boldFont)));
        bookingTable.addHeaderCell(new Cell().add(new Paragraph("Total Amount").setFont(boldFont)));
        bookingTable.addHeaderCell(new Cell().add(new Paragraph("Booking Date").setFont(boldFont)));

        for (Bookings booking : bookings) {
            bookingTable.addCell(new Cell().add(new Paragraph(String.valueOf(booking.getBookingId()))));
            bookingTable.addCell(new Cell().add(new Paragraph(booking.getUserId().getEmail())));
            bookingTable.addCell(new Cell().add(new Paragraph(String.valueOf(booking.getTotalAmount()))));
            bookingTable.addCell(new Cell().add(new Paragraph(String.valueOf(booking.getBookingDate().toLocalDate()))));
        }

        document.add(bookingTable);
    }

    private void addYearlySummary(Document document, double totalSalesYearly, int totalBookingsYearly) {
        Paragraph summary = new Paragraph("Yearly Sales Summary\n")
                .setFont(boldFont)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
        document.add(summary);

        Table summaryTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
        summaryTable.setWidth(UnitValue.createPercentValue(100)); // Full page width
        summaryTable.setMarginBottom(20);

        addTableData(summaryTable, "Total Yearly Sales:", String.valueOf(totalSalesYearly));
        addTableData(summaryTable, "Total Yearly Bookings:", String.valueOf(totalBookingsYearly));

        document.add(summaryTable);
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
