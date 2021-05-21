package com.zxj.finalexam.controller;

import com.alibaba.fastjson.JSON;
import com.zxj.finalexam.dao.CourseScoreDao;
import com.zxj.finalexam.dao.DeptUserDao;
import com.zxj.finalexam.dto.CourseScoreDTO;
import com.zxj.finalexam.dto.DetailExport;
import com.zxj.finalexam.dto.ExportDTO;
import com.zxj.finalexam.dto.SumScoreExport;
import com.zxj.finalexam.entity.DeptUserEntity;
import com.zxj.finalexam.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("finalexam/export")
public class ExportController {

    @Autowired
    private CourseScoreDao courseScoreDao;
    @Autowired
    private DeptUserDao deptUserDao;


    @RequestMapping(value = "/exportXls")
    public String exportXls(@RequestBody ExportDTO exportDTO, HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException {
        Long creator = (Long) request.getSession().getAttribute("creator");
        DeptUserEntity deptUser = deptUserDao.getDeptName(creator);
        //表头集合，作为表头参数
        List<String> titleList = new ArrayList<>();
        //数据对象
        Map<String, Object> map = new HashMap<>();
        List<DetailExport> detailExports = null;
        List<SumScoreExport> sumScoreExports = null;
        map.put("deptId", deptUser.getDeptId());
        titleList.add("科目");
        //sheet名
        String sheetName = deptUser.getName() + "sheet";
        String fileName = "";
        HSSFWorkbook workbook = null;
        if (exportDTO.getType() == 1) {
            //文件名
            fileName = "学生平时成绩明细表";
            titleList.add("任务名");
            titleList.add("学号");
            titleList.add("学生姓名");
            titleList.add("分数");
            map.put("userTaskIds", exportDTO.getKeys());
            detailExports = courseScoreDao.getAllStudentScore(map);
            //调取封装的方法，传入相应的参数
            workbook = ExcelUtil.createExcel(sheetName, titleList, detailExports, null);
        } else {
            fileName = "学生平时成绩汇总表";
            titleList.add("学号");
            titleList.add("学生姓名");
            titleList.add("总平时分");
            map.put("subjectNames", exportDTO.getKeys());
            sumScoreExports = courseScoreDao.getAllStudentSumScore(map);
            workbook = ExcelUtil.createExcel(sheetName, titleList, sumScoreExports, null);
        }
        //输出Excel文件
        //输出Excel文件
        OutputStream output = response.getOutputStream();
        response.reset();
        //中文名称要进行编码处理
        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1") + ".xlsx");
        response.setContentType("application/octet-stream;charset=UTF-8");
        workbook.write(output);

        output.close();
        return null;
    }

}
