package com.zxj.finalexam.utils;

import com.zxj.finalexam.dto.DetailExport;
import com.zxj.finalexam.entity.OutExcelQuery;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExcelUtil {

    /**
     *  生成Excel表格
     * @param sheetName sheet名称
     * @param titleList 表头列表
     * @param dataList 数据列表
     * @param outExcelQuery 下拉选项设置
     * @return HSSFWorkbook对象
     * */
    public static HSSFWorkbook createExcel(String sheetName, List<String> titleList,
                                           List dataList, OutExcelQuery outExcelQuery) throws IllegalAccessException {

        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet(sheetName);
        //在sheet里创建第一行，这里即是表头
        HSSFRow rowTitle=sheet.createRow(0);

        //写入表头的每一个列
        for (int i = 0; i < titleList.size(); i++) {
            //创建单元格
            rowTitle.createCell(i).setCellValue(titleList.get(i));
        }

        int count = 0;
        //写入每一行的记录
        if (dataList!=null){
            for (int i = 0; i < dataList.size(); i++) {
                count++;
                //创建新的一行，递增
                HSSFRow rowData = sheet.createRow(i+1);

                //通过反射，获取POJO对象
                Class cl = dataList.get(i).getClass();
                //获取类的所有字段
                Field[] fields = cl.getDeclaredFields();
                for (int j = 0; j < titleList.size(); j++) {
                    //设置字段可见，否则会报错，禁止访问
                    fields[j].setAccessible(true);
                    //创建单元格

                    rowData.createCell(j).setCellValue(String.valueOf(fields[j].get(dataList.get(i))));
                }
            }
        }

        //如果开启了下拉选项
        if (outExcelQuery!=null){
            //如果表格中的记录数不是0
            if (count!=0){
                // 获取下拉列表数据
                String[] strs = outExcelQuery.getParams();
                //设置哪些行的哪些列为下拉选项
                CellRangeAddressList rangeList =
                        new CellRangeAddressList(outExcelQuery.getRowStart(),
                                //结束行为-1时，说明设置所有行
                                outExcelQuery.getRowEnd()==-1?count:outExcelQuery.getRowEnd(),
                                outExcelQuery.getColStart(),outExcelQuery.getColEnd());
                //绑定下拉数据
                DVConstraint constraint = DVConstraint.createExplicitListConstraint(strs);
                //绑定两者的关系
                HSSFDataValidation dataValidation = new HSSFDataValidation(rangeList,constraint);
                //添加到sheet中
                sheet.addValidationData(dataValidation);
            }
        }
        return wb;
    }

    /**
     * 读入excel文件，解析后返回
     * @param file
     * @throws IOException
     */
    public static List<String[]> readExcel(MultipartFile file) throws IOException{
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    //循环当前行
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
            workbook.close();
        }
        return list;
    }

    /**
     * 检查用户上传的文件
     * @param file 文件对象
     * */
    private static void checkFile(MultipartFile file) throws IOException{
        //判断文件是否存在
        if(null == file){
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if(!fileName.endsWith("xls") && !fileName.endsWith("xlsx")){
            throw new IOException(fileName + "不是excel文件");
        }
    }

    /**
     *  获取Workbook对象
     * @param file 文件对象
     * @return Workbook对象
     * */
    private static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith("xls")){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith("xlsx")){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {

        }
        return workbook;
    }

    /**
     *  获取单元格的值
     * @param cell 单元格对象
     * @return 值
     * */
    private static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "未填写";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}