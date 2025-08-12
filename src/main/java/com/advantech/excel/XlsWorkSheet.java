/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.excel;

import static com.google.common.collect.Lists.newArrayList;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.activation.UnsupportedDataTypeException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.dbunit.dataset.excel.XlsDataSetWriter;
import org.joda.time.DateTime;

/**
 *
 * @author Wei.Cheng
 */
public class XlsWorkSheet {

    private final HSSFSheet _sheet;
    private final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    private final List<String> _columnList = new ArrayList<>();
    private int _maxColumnNum = 0;
    private List<String> _columnList_except = newArrayList(
            "totalModule", "cleanPanel", "assy", "t1", "t2",
            "t3", "t4", "hiPotLeakage", "coldBoot", "warmBoot",
            "vibration", "upBiRi", "downBiRi", "packing", "assyStation",
            "packingStation", "productionWt",
            "workCenter", "machineWorktime", "setupTime", "biCost",
            "assyToT1", "t2ToPacking", "biPower",
            "assyLeadTime", "assyKanbanTime", "packingLeadTime",
            "packingPalletTime", "packingKanbanTime", "cleanPanelAndAssembly", "sapWt", "reasonCode");

    public XlsWorkSheet(String sheetName, HSSFSheet sheet) {
        _sheet = sheet;
        symbols.setDecimalSeparator('.');

        if (_sheet.getLastRowNum() > 0) {
            createColumnList(_sheet.getRow(0));
        }
    }

    private void createColumnList(HSSFRow sampleRow) {
        for (int i = 0;; i++) {
            HSSFCell cell = sampleRow.getCell(i);
            if (cell == null) {
                break;
            }

            String columnName = cell.getRichStringCellValue().getString();
            if (columnName != null) {
                columnName = columnName.trim();
            }

            // Bugfix for issue ID 2818981 - if a cell has a formatting but no name also ignore it  
            if (columnName == null || columnName.length() <= 0) {
                break;
            }
            _maxColumnNum = i;
            //Column column = new Column(columnName, DataType.UNKNOWN);  
            _columnList.add(columnName.toUpperCase());
        }
        //Column[] columns = (Column[])_columnList.toArray(new Column[0]);  

    }

    /**
     * 功能说明：得到行数
     *
     * @return
     */
    public int getRowCount() {
//        return _sheet.getPhysicalNumberOfRows();
        return _sheet.getLastRowNum();
    }

    /**
     * 功能说明：得到列数
     *
     * @return
     */
    public int getMaxColumnNum() {
        return _maxColumnNum;
    }

    /**
     * 功能说明：根据列名得到列号
     *
     * @param column
     * @return
     */
    private int getColumnIndex(String column) {

        return _columnList.indexOf(column.toUpperCase());
    }

    public Object getValue(int row, String column) throws UnsupportedDataTypeException {
        int columnIndex = getColumnIndex(column);
        HSSFCell cell = _sheet.getRow(row + 1).getCell(columnIndex);
        if (cell == null) {
            return "";
        }

        CellType type = cell.getCellType();
        switch (type) {
            case NUMERIC:
                HSSFCellStyle style = cell.getCellStyle();
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    return getDateValue(cell);
                } else if (XlsDataSetWriter.DATE_FORMAT_AS_NUMBER_DBUNIT.equals(style.getDataFormatString())) {
                    // The special dbunit date format  
                    return getDateValueFromJavaNumber(cell);
                } else {
                    return getNumericValue(cell);
                }

            case STRING:
                return cell.getRichStringCellValue().getString();

            case FORMULA:
                throw new UnsupportedDataTypeException("Formula not supported at row="
                        + row + ", column=" + column);

            case BLANK:
                return "";

            case BOOLEAN:
                return cell.getBooleanCellValue() ? Boolean.TRUE : Boolean.FALSE;

            case ERROR:
                throw new UnsupportedDataTypeException("Error at row=" + row
                        + ", column=" + column);

            default:
                throw new UnsupportedDataTypeException("Unsupported type at row=" + row
                        + ", column=" + column);
        }
    }

    protected Object getDateValueFromJavaNumber(HSSFCell cell) {

        double numericValue = cell.getNumericCellValue();
        BigDecimal numericValueBd = new BigDecimal(String.valueOf(numericValue));
        numericValueBd = stripTrailingZeros(numericValueBd);
        return numericValueBd.longValue();
//        return new Long(numericValueBd.unscaledValue().longValue());  
    }

    protected Object getDateValue(HSSFCell cell) {

        double numericValue = cell.getNumericCellValue();
        Date date = HSSFDateUtil.getJavaDate(numericValue);
        // Add the timezone offset again because it was subtracted automatically by Apache-POI (we need UTC)  
        long tzOffset = TimeZone.getDefault().getOffset(date.getTime());
        date = new Date(date.getTime() + tzOffset);
        return date.getTime();

    }

    /**
     * Removes all trailing zeros from the end of the given BigDecimal value up
     * to the decimal point.
     *
     * @param value The value to be stripped
     * @return The value without trailing zeros
     */
    private BigDecimal stripTrailingZeros(BigDecimal value) {
        if (value.scale() <= 0) {
            return value;
        }

        String valueAsString = String.valueOf(value);
        int idx = valueAsString.indexOf(".");
        if (idx == -1) {
            return value;
        }

        OUTER:
        for (int i = valueAsString.length() - 1; i > idx; i--) {
            switch (valueAsString.charAt(i)) {
                case '0':
                    valueAsString = valueAsString.substring(0, i);
                    break;
                case '.':
                    valueAsString = valueAsString.substring(0, i);
                    // Stop when decimal point is reached
                    break OUTER;
                default:
                    break OUTER;
            }
        }
        BigDecimal result = new BigDecimal(valueAsString);
        return result;
    }

    protected BigDecimal getNumericValue(HSSFCell cell) {
        String formatString = cell.getCellStyle().getDataFormatString();
        String resultString = null;
        double cellValue = cell.getNumericCellValue();

        if ((formatString != null)) {
            if (!formatString.equals("General") && !formatString.equals("@")) {
                DecimalFormat nf = new DecimalFormat(formatString, symbols);
                resultString = nf.format(cellValue);
            }
        }

        BigDecimal result;
        if (resultString != null) {
            try {
                result = new BigDecimal(resultString);
            } catch (NumberFormatException e) {
                result = toBigDecimal(cellValue);
            }
        } else {
            result = toBigDecimal(cellValue);
        }
        return result;
    }

    /**
     * @param cellValue
     * @return
     * @since 2.4.6
     */
    private BigDecimal toBigDecimal(double cellValue) {
        String resultString = String.valueOf(cellValue);
        // To ensure that intergral numbers do not have decimal point and trailing zero  
        // (to restore backward compatibility and provide a string representation consistent with Excel)  
        if (resultString.endsWith(".0")) {
            resultString = resultString.substring(0, resultString.length() - 2);
        }
        BigDecimal result = new BigDecimal(resultString);
        return result;

    }

    public <T extends Object> List<T> buildBeans(Class cls, Map<String, T> modelMap) throws Exception {
        List<T> list = new ArrayList<>();

        DataFormatter formatter = new DataFormatter();
        _columnList_except.replaceAll(String::toUpperCase);
        HSSFCell modelNameCell = _sheet.getRow(0).getCell(1);
        String modelNameColumn = formatter.formatCellValue(modelNameCell);

        Method[] ms = cls.getDeclaredMethods();
        for (int row = 0, rowCount = this.getRowCount(); row < rowCount; row++) {
            HSSFCell checkedCell = _sheet.getRow(row).getCell(0);
            HSSFCell checkedCell2 = _sheet.getRow(row).getCell(1);
            if (checkedCell == null || checkedCell.getCellType() == CellType.BLANK
                    || checkedCell2 == null || checkedCell2.getCellType() == CellType.BLANK
                    || ("".equals(formatter.formatCellValue(checkedCell)) && "".equals(formatter.formatCellValue(checkedCell2)))) {
                continue;
            }

            String modelName = this.getValue(row, modelNameColumn).toString();
            T bean = modelMap.getOrDefault(modelName, (T) cls.newInstance());
            for (Method m : ms) {
                String methodName = m.getName().toUpperCase();
                if (methodName.startsWith("SET")
                        && this._columnList.contains(methodName.substring(3))
                        && !_columnList_except.contains(methodName.substring(3))) {

                    //System.out.println(ms[i].getGenericParameterTypes()[0].toString());  
                    String val = this.getValue(row, methodName.substring(3)).toString();
                    if (val == null) {
                        continue;
                    }
                    String pType = m.getGenericParameterTypes()[0].toString();
                    if (pType.contains("java.util.List") || pType.contains("java.util.Set")
                            || ("".equals(val) && !pType.contains("java.lang.String"))) {
                        continue;
                    }
                    switch (pType) {
                        case "class java.lang.Integer":
                        case "int":
                            m.invoke(bean, Integer.parseInt(val));
                            break;
                        case "float":
                            m.invoke(bean, Float.parseFloat(val));
                            break;
                        case "boolean":
                            m.invoke(bean, Boolean.parseBoolean(val));
                            break;
                        case "double":
                            m.invoke(bean, Double.parseDouble(val));
                            break;
                        case "short":
                            m.invoke(bean, Short.parseShort(val));
                            break;
                        case "long":
                            m.invoke(bean, Long.parseLong(val));
                            break;
                        case "class java.lang.Character":
                        case "char":
                            m.invoke(bean, val.charAt(0));
                            break;
                        case "class java.math.BigDecimal":
                            m.invoke(bean, new BigDecimal(val));
                            break;
                        case "class java.util.Date":
                            DateTime d = new DateTime(val);
                            m.invoke(bean, d.toDate());
                            break;
                        case "class java.lang.String":
                        default:
                            m.invoke(bean, val);
                            break;
                    }
                }
            }
            list.add(bean);
        }

        return list;
    }
}
