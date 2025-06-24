package org.example.services;


import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.model.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExportService {

    public void exportReportToPDF(String filePath, ACSPLUSInput input, ACSPLUSOutput output, List<IcmalRow> icmalRows) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Paragraph("ACSPLUS RAPORU", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20)));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Proje: " + input.projectName));
            document.add(new Paragraph("Müşteri: " + input.clientName));
            document.add(new Paragraph("Yaz Üfleme Sıcaklığı (°C): " +
                    input.summerSupplyTemp));
            document.add(new Paragraph("Kış Üfleme Sıcaklığı (°C): " +
                    input.winterSupplyTemp));
            document.add(new Paragraph(" "));

            // Icmal Tablosu
            document.add(new Paragraph("ICMAL"));
            com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(6);
            table.addCell("Cihaz");
            table.addCell("Kapasite");
            table.addCell("Debi");
            table.addCell("Güç");
            table.addCell("Fiyat");
            table.addCell("Toplam");

            for (IcmalRow row : icmalRows) {
                table.addCell(row.getDevice());
                table.addCell(String.valueOf(row.getCapacity()));
                table.addCell(String.valueOf(row.getAirflow()));
                table.addCell(String.valueOf(row.getPower()));
                table.addCell(String.valueOf(row.getPrice()));
                table.addCell(String.valueOf(row.getTotal()));
            }
            document.add(table);

            document.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

        public void exportReportToExcel(String filePath, ACSPLUSInput input, ACSPLUSOutput output, List<IcmalRow> icmalRows) throws IOException {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Rapor");
                int rowNum = 0;

                // Başlık
                Row title = sheet.createRow(rowNum++);
                title.createCell(0).setCellValue("ACSPLUS RAPORU");
                rowNum++;

                // Proje/Müşteri ve Set‐Temp
                sheet.createRow(rowNum++).createCell(0)
                        .setCellValue("Proje: " + input.projectName);
                sheet.createRow(rowNum++).createCell(0)
                        .setCellValue("Müşteri: " + input.clientName);
                sheet.createRow(rowNum++).createCell(0)
                        .setCellValue("Yaz Üfleme Sıcaklığı (°C): " +
                                input.summerSupplyTemp);
                sheet.createRow(rowNum++).createCell(0)
                        .setCellValue("Kış Üfleme Sıcaklığı (°C): " +
                                input.winterSupplyTemp);
                rowNum++;

                // Icmal Tablosu Başlık
                Row header = sheet.createRow(rowNum++);
                header.createCell(0).setCellValue("Cihaz");
                header.createCell(1).setCellValue("Kapasite");
                header.createCell(2).setCellValue("Debi");
                header.createCell(3).setCellValue("Güç");
                header.createCell(4).setCellValue("Fiyat");
                header.createCell(5).setCellValue("Toplam");

                // Satırlar
                for (IcmalRow r : icmalRows) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(r.getDevice());
                    row.createCell(1).setCellValue(r.getCapacity());
                    row.createCell(2).setCellValue(r.getAirflow());
                    row.createCell(3).setCellValue(r.getPower());
                    row.createCell(4).setCellValue(r.getPrice());
                    row.createCell(5).setCellValue(r.getTotal());
                }

                // Dosyaya yaz
                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    workbook.write(fos);
                }
            }
        }

}

