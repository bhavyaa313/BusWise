package buswise.service;

import buswise.dao.BookingDao;
import buswise.dto.OccupancyReportDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class YearlyOccupancyReportPdf {

    @Autowired
    private BookingDao bookingDao;

    public String createYearlyOccupancyPdf(HttpServletRequest request, int year) {
        String yearString = String.valueOf(year);

        String filePath = request.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "resources"
                + File.separator + "reports" + File.separator + "YearlyOccupancyReport_" + yearString + ".pdf";

        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdfDoc);
            PdfFont boldFont = PdfFontFactory.createFont();

            document.setMargins(50, 50, 50, 50);

            Paragraph header = new Paragraph("Yearly Occupancy Report\n")
                    .setFont(boldFont)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10);
            document.add(header);

            Paragraph dateParagraph = new Paragraph("Year: " + yearString + "\n")
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
            table.addHeaderCell(new Cell().add(new Paragraph("Route").setFont(boldFont)));
            table.addHeaderCell(new Cell().add(new Paragraph("RouteID").setFont(boldFont)));
            table.addHeaderCell(new Cell().add(new Paragraph("Total Seats").setFont(boldFont)));
            table.addHeaderCell(new Cell().add(new Paragraph("Seats Occupied").setFont(boldFont)));
            table.addHeaderCell(new Cell().add(new Paragraph("Occupancy %").setFont(boldFont)));

            for (int month = 1; month <= 12; month++) {
                String monthString = String.format("%02d", month);
                String dateString = yearString + "-" + monthString;
                List<OccupancyReportDto> reportDataList = bookingDao.getMonthlyOccupancyData(year, month);

                for (OccupancyReportDto reportData : reportDataList) {
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(reportData.getScheduleId()))));
                    table.addCell(new Cell().add(new Paragraph(reportData.getSelectedSource())));
                    table.addCell(new Cell().add(new Paragraph(reportData.getSelectedDestination())));
                    table.addCell(new Cell().add(new Paragraph(reportData.getRoute())));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(reportData.getRouteId()))));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(reportData.getTotalSeats()))));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(reportData.getSeatsOccupied()))));
                    table.addCell(new Cell().add(new Paragraph(String.format("%.2f", reportData.getOccupancyPercentage()))));
                }
            }

            document.add(table);

            Paragraph footer = new Paragraph("Thank you ")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12)
                    .setMarginTop(20);
            document.add(footer);
            document.close();

            System.out.println("PDF created successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }

}
