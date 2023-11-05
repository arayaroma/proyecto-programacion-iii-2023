package cr.ac.una.clinicaunaws.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import cr.ac.una.clinicaunaws.dto.ReportDto;

/**
 * 
 * @author arayaroma
 */
public class ExcelGenerator {
    private static ExcelGenerator instance;

    public static ExcelGenerator getInstance() {
        if (instance == null) {
            instance = new ExcelGenerator();
        }
        return instance;
    }

    private ExcelGenerator() {
    }

    public CellStyle createStyleHeader(Workbook workbook, HorizontalAlignment horizontalAlignment,
            IndexedColors colors) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();

        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        cellStyle.setFont(headerFont);
        cellStyle.setAlignment(horizontalAlignment);
        cellStyle.setFillForegroundColor(colors.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    public File generateExcelReport(ReportDto report) throws IOException {
        List<String> headerNames = QueryManager.extractAlias(report.getQueryManager().getQuery());

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");
        CellStyle style = createStyleHeader(workbook, HorizontalAlignment.CENTER, IndexedColors.BLUE);

        Row headerRow = sheet.createRow(0);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headerNames.size()));
        Cell cell = headerRow.createCell(0);

        for (int i = 0; i < headerNames.size(); i++) {
            cell = headerRow.createCell(i);
            cell.setCellValue(headerNames.get(i));
            cell.setCellStyle(style);
        }

        int randomNumber = (int) (Math.random() * 1000);
        String randomNumberString = String.valueOf(randomNumber).substring(0, 3);

        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String projectRoot = System.getProperty("user.dir");
        String outputPath = "reports/" + report.getName() + randomNumberString + "-" + currentDate + ".xlsx";
        String filePath = projectRoot + "/" + outputPath;

        File excelFile = new File(filePath);
        excelFile.getParentFile().mkdirs();

        FileOutputStream outputStream = new FileOutputStream(excelFile);
        workbook.write(outputStream);
        outputStream.close();
        return excelFile;
    }

}
