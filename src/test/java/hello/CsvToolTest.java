package hello;

import com.google.common.collect.Lists;
import domain.CsvExport;
import org.junit.Test;
import util.CsvTool;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @program: GradleSpringBoot
 * @description:
 * @author: songleilei
 * @create: 2019-08-25 12:27
 **/
public class CsvToolTest {
    @Test
    public void testCreateCsvFile() {
        String filePath = "J:\\Study\\test.csv";
        List<CsvExport> csvExports = getData();

        CsvTool.newInstance().createCsvFile(filePath, csvExports, CsvExport.class);
        boolean isFileOK = isFileOK(filePath);
        assertTrue(isFileOK);
    }

    private boolean isFileOK(String filePath) {

        File file = new File(filePath);
        return file.isFile();
    }

    private List<CsvExport> getData() {
        List<CsvExport> csvExports = Lists.newArrayList();
        CsvExport csvExport = new CsvExport("songleilei", "男", "123", BigDecimal.valueOf(200));
        csvExports.add(csvExport);
        return csvExports;
    }
}
