package util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @program: GradleSpringBoot
 * @description: csv文件生成工具
 * @author: songleilei
 * @create: 2019-08-25 12:18
 **/
public final class CsvTool {

    private DynamicDisplay dynamicDisplay;

    private CsvTool(DynamicDisplay dynamicDisplay) {
        this.dynamicDisplay = dynamicDisplay;
    }

    public static CsvTool newInstance() {
        return newInstance(name -> true);
    }

    public static CsvTool newInstance(DynamicDisplay dynamicDisplay) {
        return new CsvTool(dynamicDisplay);
    }

    public <T> void createCsvFile(String filePath, List<T> csvExports, Class<T> tClass) {
        if (CollectionUtils.isEmpty(csvExports)) {
            List<String> headers = generateHeader(tClass);
            List<List<String>> data = newArrayList();
            data.add(headers);
            generateFile(filePath, data);
            return;
        }

        List<List<String>> data = generateData(csvExports, tClass);
        generateFile(filePath, data);
    }

    private <T> List<String> generateHeader(Class<T> tClass) {
        List<Field> fields = Arrays.asList(tClass.getDeclaredFields());
        List<String> headers = newArrayList();
        fields.forEach(field -> {
            CsvHeaderAttribute csvHeaderAttribute = field.getAnnotation(CsvHeaderAttribute.class);

            if (isDisplay(csvHeaderAttribute)) {
                headers.add(csvHeaderAttribute.name());
            }

        });
        return headers;
    }

    private <T> List<List<String>> generateData(List<T> csvExports, Class<T> tClass) {

        List<String> headers = generateHeader(tClass);
        List<List<String>> rowsData = generateRowsData(csvExports, tClass);
        List<List<String>> data = newArrayList();
        data.add(headers);
        data.addAll(rowsData);
        return data;
    }

    private <T> List<List<String>> generateRowsData(List<T> csvExports, Class<T> tClass) {
        List<List<String>> data = newArrayList();
        csvExports.forEach(csvExport -> {
            List<String> singleRowData = generateSingleRowData(csvExport, tClass);
            data.add(singleRowData);
        });
        return data;
    }

    private <T> List<String> generateSingleRowData(T csvExport, Class<T> tClass) {
        List<Field> fields = Arrays.asList(tClass.getDeclaredFields());
        List<String> singleRow = newArrayList();
        fields.forEach(field -> {
            CsvAttribute csvAttribute = field.getAnnotation(CsvAttribute.class);
            CsvHeaderAttribute csvHeaderAttribute = field.getAnnotation(CsvHeaderAttribute.class);
            field.setAccessible(true);
            if (!isDisplay(csvHeaderAttribute)) {
                return;
            }

            Object fieldValue = null;
            try {
                fieldValue = field.get(csvExport);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (csvAttribute.isMoney()) {
                String defaultDecimalFormat = ",###.00";
                DecimalFormat decimalFormat = new DecimalFormat(
                        StringUtils.isBlank(csvAttribute.decimalFormat()) ?
                                defaultDecimalFormat :
                                csvAttribute.decimalFormat());
                String amount = decimalFormat.format(fieldValue);
                singleRow.add(amount);
            } else {
                singleRow.add((String) fieldValue);
            }


        });
        return singleRow;
    }

    private void generateFile(String filePath, List<List<String>> data) {
        File file = new File(filePath);
        List<String> csvData = transferToCsvData(data);
        try {
            FileUtils.writeLines(file, "utf-8", csvData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> transferToCsvData(List<List<String>> data) {
        return data.stream().map(this::transferToCsvRow).collect(Collectors.toList());
    }

    private String transferToCsvRow(List<String> row) {
        return String.join(",", row);
    }

    private boolean isDisplay(CsvHeaderAttribute csvHeaderAttribute) {
        boolean isDisplay;
        if (csvHeaderAttribute.dynamicDisplay()) {
            isDisplay = dynamicDisplay.isDisplay(csvHeaderAttribute.name());
        } else {
            isDisplay = csvHeaderAttribute.isDisplay();
        }
        return isDisplay;
    }
}
