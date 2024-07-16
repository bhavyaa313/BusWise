package buswise.service;


import buswise.dao.BookingDao;
import buswise.dto.OccupancyReportDto;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DailyOccupancyReportPdf {

    @Autowired
    private BookingDao bookingDao;

    public String createDailyOccupancyPdf(HttpServletRequest request, String dateString) {
        String filePath = request.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "resources"
                + File.separator + "reports" + File.separator + "DailyOccupancyReport_" + dateString + ".pdf";

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateString, formatter);

            List<OccupancyReportDto> reportDataList = bookingDao.getDailyOccupancyData(localDate);

            // Initialize PDF writer
            PdfWriter writer = new PdfWriter(filePath);

            // Initialize PDF document
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.setDefaultPageSize(PageSize.A4);

            // Initialize document
            Document document = new Document(pdfDoc);

            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            document.setMargins(50, 50, 50, 50);


            Paragraph header = new Paragraph("Daily Occupancy Report\n")
                    .setFont(boldFont)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10);
            document.add(header);


            Paragraph dateParagraph = new Paragraph("Date: " + dateString + "\n")
                    .setFont(boldFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(dateParagraph);


            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 1, 1, 1, 1}));
            table.setWidth(UnitValue.createPercentValue(100));


            table.addHeaderCell(new Cell().add(new Paragraph("Schedule ID").setFont(boldFont)));
            table.addHeaderCell(new Cell().add(new Paragraph("Source").setFont(boldFont)));
            table.addHeaderCell(new Cell().add(new Paragraph("Destination").setFont(boldFont)));
            table.addHeaderCell(new Cell().add(new Paragraph("Departure Time").setFont(boldFont)));
            table.addHeaderCell(new Cell().add(new Paragraph("Total Seats").setFont(boldFont)));
            table.addHeaderCell(new Cell().add(new Paragraph("Seats Occupied").setFont(boldFont)));
            table.addHeaderCell(new Cell().add(new Paragraph("Occupancy %").setFont(boldFont)));


            for (OccupancyReportDto reportData : reportDataList) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(reportData.getScheduleId()))));
                table.addCell(new Cell().add(new Paragraph(reportData.getSelectedSource())));
                table.addCell(new Cell().add(new Paragraph(reportData.getSelectedDestination())));
                table.addCell(new Cell().add(new Paragraph(reportData.getDepartureTime())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(reportData.getTotalSeats()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(reportData.getSeatsOccupied()))));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f", reportData.getOccupancyPercentage()))));
            }

            document.add(table);


            Paragraph footer = new Paragraph("Thank you for choosing BusWise Travels!\nTerms and Conditions apply. Please carry a valid ID proof during the journey.")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12)
                    .setMarginTop(20);
            document.add(footer);


            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }

}
