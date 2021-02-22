package org.javaboy.vhr.utils;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.Na;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.javaboy.vhr.model.*;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class POIUtils {

    public static ResponseEntity emp2Excel(List<Employee> employeeList) {
        // ① 创建workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        // ② 创建文档摘要
        workbook.createInformationProperties();
        // ③ 获取并配置文档摘要信息
        DocumentSummaryInformation docSumInfo = workbook.getDocumentSummaryInformation();
        // 文档类别
        docSumInfo.setCategory("员工信息");
        // 文档管理员
        docSumInfo.setManager("Zero");
        // 公司信息
        docSumInfo.setCompany("Zero@com");
        // ④ 获取摘要信息
        SummaryInformation sumInfo = workbook.getSummaryInformation();
        // 文档标题
        sumInfo.setTitle("员工信息表");
        // 文档作者
        sumInfo.setAuthor("Zero");
        // 文档备注
        sumInfo.setComments("本文档由 Zero 提供");

        // ⑤ 创建sheet并设置sheet名
        HSSFSheet sheet = workbook.createSheet("员工信息表");
        // 设置列宽
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 15 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 5 * 256);
        sheet.setColumnWidth(4, 10 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 10 * 256);
        sheet.setColumnWidth(8, 10 * 256);
        sheet.setColumnWidth(9, 15 * 256);
        sheet.setColumnWidth(10, 20 * 256);
        sheet.setColumnWidth(11, 15 * 256);
        sheet.setColumnWidth(12, 30 * 256);
        sheet.setColumnWidth(13, 10 * 256);
        sheet.setColumnWidth(14, 15 * 256);
        sheet.setColumnWidth(15, 20 * 256);
        sheet.setColumnWidth(16, 10 * 256);
        sheet.setColumnWidth(17, 10 * 256);
        sheet.setColumnWidth(18, 10 * 256);
        sheet.setColumnWidth(19, 10 * 256);
        sheet.setColumnWidth(20, 10 * 256);
        sheet.setColumnWidth(21, 10 * 256);
        sheet.setColumnWidth(22, 10 * 256);
        sheet.setColumnWidth(23, 15 * 256);
        sheet.setColumnWidth(24, 15 * 256);
        sheet.setColumnWidth(25, 15 * 256);
        // ⑥ 设置header行样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFCellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        // ⑥ 创建header行
        HSSFRow r0 = sheet.createRow(0);
        HSSFCell r0c0 = r0.createCell(0);
        r0c0.setCellValue("编号");
        r0c0.setCellStyle(headerStyle);
        HSSFCell r0c1 = r0.createCell(1);
        r0c1.setCellValue("姓名");
        r0c1.setCellStyle(headerStyle);
        HSSFCell r0c2 = r0.createCell(2);
        r0c2.setCellValue("工号");
        r0c2.setCellStyle(headerStyle);
        HSSFCell r0c3 = r0.createCell(3);
        r0c3.setCellValue("性别");
        r0c3.setCellStyle(headerStyle);
        HSSFCell r0c4 = r0.createCell(4);
        r0c4.setCellValue("出生日期");
        r0c4.setCellStyle(headerStyle);
        HSSFCell r0c5 = r0.createCell(5);
        r0c5.setCellValue("身份证号码");
        r0c5.setCellStyle(headerStyle);
        HSSFCell r0c6 = r0.createCell(6);
        r0c6.setCellValue("婚姻状况");
        r0c6.setCellStyle(headerStyle);
        HSSFCell r0c7 = r0.createCell(7);
        r0c7.setCellValue("民族");
        r0c7.setCellStyle(headerStyle);
        HSSFCell r0c8 = r0.createCell(8);
        r0c8.setCellValue("籍贯");
        r0c8.setCellStyle(headerStyle);
        HSSFCell r0c9 = r0.createCell(9);
        r0c9.setCellValue("政治面貌");
        r0c9.setCellStyle(headerStyle);
        HSSFCell r0c10 = r0.createCell(10);
        r0c10.setCellValue("电子邮件");
        r0c10.setCellStyle(headerStyle);
        HSSFCell r0c11 = r0.createCell(11);
        r0c11.setCellValue("电话号码");
        r0c11.setCellStyle(headerStyle);
        HSSFCell r0c12 = r0.createCell(12);
        r0c12.setCellValue("联系地址");
        r0c12.setCellStyle(headerStyle);
        HSSFCell r0c13 = r0.createCell(13);
        r0c13.setCellValue("最高学历");
        r0c13.setCellStyle(headerStyle);
        HSSFCell r0c14 = r0.createCell(14);
        r0c14.setCellValue("毕业院校");
        r0c14.setCellStyle(headerStyle);
        HSSFCell r0c15 = r0.createCell(15);
        r0c15.setCellValue("专业名称");
        r0c15.setCellStyle(headerStyle);
        HSSFCell r0c16 = r0.createCell(16);
        r0c16.setCellValue("在职状态");
        r0c16.setCellStyle(headerStyle);
        HSSFCell r0c17 = r0.createCell(17);
        r0c17.setCellValue("所属部门");
        r0c17.setCellStyle(headerStyle);
        HSSFCell r0c18 = r0.createCell(18);
        r0c18.setCellValue("职位");
        r0c18.setCellStyle(headerStyle);
        HSSFCell r0c19 = r0.createCell(19);
        r0c19.setCellValue("职称");
        r0c19.setCellStyle(headerStyle);
        HSSFCell r0c20 = r0.createCell(20);
        r0c20.setCellValue("聘用形式");
        r0c20.setCellStyle(headerStyle);
        HSSFCell r0c21 = r0.createCell(21);
        r0c21.setCellValue("入职日期");
        r0c21.setCellStyle(headerStyle);
        HSSFCell r0c22 = r0.createCell(22);
        r0c22.setCellValue("转正日期");
        r0c22.setCellStyle(headerStyle);
        HSSFCell r0c23 = r0.createCell(23);
        r0c23.setCellValue("合同起始日期");
        r0c23.setCellStyle(headerStyle);
        HSSFCell r0c24 = r0.createCell(24);
        r0c24.setCellValue("合同截止日期");
        r0c24.setCellStyle(headerStyle);
        HSSFCell r0c25 = r0.createCell(25);
        r0c25.setCellValue("合同期限(年)");
        r0c25.setCellStyle(headerStyle);

        for (int i = 0; i < employeeList.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(employeeList.get(i).getId());
            row.createCell(1).setCellValue(employeeList.get(i).getName());
            row.createCell(2).setCellValue(employeeList.get(i).getWorkID());
            row.createCell(3).setCellValue(employeeList.get(i).getGender());
            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(employeeList.get(i).getBirthday());
            cell4.setCellStyle(dateStyle);
            row.createCell(5).setCellValue(employeeList.get(i).getIdCard());
            row.createCell(6).setCellValue(employeeList.get(i).getWedlock());
            row.createCell(7).setCellValue(employeeList.get(i).getNation().getName());
            row.createCell(8).setCellValue(employeeList.get(i).getNativePlace());
            row.createCell(9).setCellValue(employeeList.get(i).getPoliticsstatus().getName());
            row.createCell(10).setCellValue(employeeList.get(i).getEmail());
            row.createCell(11).setCellValue(employeeList.get(i).getPhone());
            row.createCell(12).setCellValue(employeeList.get(i).getAddress());
            row.createCell(13).setCellValue(employeeList.get(i).getTiptopDegree());
            row.createCell(14).setCellValue(employeeList.get(i).getSchool());
            row.createCell(15).setCellValue(employeeList.get(i).getSpecialty());
            row.createCell(16).setCellValue(employeeList.get(i).getWorkState());
            row.createCell(17).setCellValue(employeeList.get(i).getDepartment().getName());
            row.createCell(18).setCellValue(employeeList.get(i).getPosition().getName());
            row.createCell(19).setCellValue(employeeList.get(i).getJobLevel().getName());
            row.createCell(20).setCellValue(employeeList.get(i).getEngageForm());
            HSSFCell cell21 = row.createCell(21);
            cell21.setCellValue(employeeList.get(i).getBeginDate());
            cell21.setCellStyle(dateStyle);
            HSSFCell cell22 = row.createCell(22);
            cell22.setCellValue(employeeList.get(i).getConversionTime());
            cell22.setCellStyle(dateStyle);
            HSSFCell cell23 = row.createCell(23);
            cell23.setCellValue(employeeList.get(i).getBeginContract());
            cell23.setCellStyle(dateStyle);
            HSSFCell cell24 = row.createCell(24);
            cell24.setCellValue(employeeList.get(i).getEndContract());
            cell24.setCellStyle(dateStyle);
            row.createCell(25).setCellValue(employeeList.get(i).getContractTerm());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment", new String("员工表.xls".getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }

    public static List<Employee> excel2Emp(MultipartFile file, List<Nation> allNations, List<Politicsstatus> allPoliticsstatus, List<Department> allDepartments, List<Position> allPositions, List<JobLevel> allJobLevels) {
        List<Employee> employeeList = new ArrayList<>();
        Employee employee = null;

        try {
            // 1. 创建workbook
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());

            // 2. 获取sheet数
            int numberOfSheets = workbook.getNumberOfSheets();

            for (int i = 0; i < numberOfSheets; i++) {
                // 3. 获取当前sheet
                HSSFSheet sheet = workbook.getSheetAt(i);
                // 4. 获取行数
                int numberOfRows = sheet.getPhysicalNumberOfRows();
                for (int j = 0; j < numberOfRows; j++) {
                    // 如果为标题行，跳过
                    if (j == 0) continue;
                    // 5. 获取当前行
                    HSSFRow row = sheet.getRow(j);
                    // 如果当前行为空行，跳过
                    if (row == null) continue;

                    employee = new Employee();

                    // 6. 获取列数
                    int numberOfCells = row.getPhysicalNumberOfCells();
                    for (int k = 0; k < numberOfCells; k++) {
                        // 7. 获取当前列
                        HSSFCell cell = row.getCell(k);
                        switch (cell.getCellType()) {
                            case STRING:
                                String cellValue = cell.getStringCellValue();
                                switch (k) {
                                    case 1: // 姓名
                                        employee.setName(cellValue);
                                        break;
                                    case 2: // 工号
                                        employee.setWorkID(cellValue);
                                        break;
                                    case 3: // 性别
                                        employee.setGender(cellValue);
                                        break;
                                    case 5: // 身份证号码
                                        employee.setIdCard(cellValue);
                                        break;
                                    case 6: // 婚姻状况
                                        employee.setWedlock(cellValue);
                                        break;
                                    case 7: // 民族
                                        int nationIndex = allNations.indexOf(new Nation(cellValue));
                                        employee.setNationId(allNations.get(nationIndex).getId());
                                        break;
                                    case 8: // 籍贯
                                        employee.setNativePlace(cellValue);
                                        break;
                                    case 9: // 政治面貌
                                        int politicstatusIndex = allPoliticsstatus.indexOf(new Politicsstatus(cellValue));
                                        employee.setPoliticId(allPoliticsstatus.get(politicstatusIndex).getId());
                                        break;
                                    case 10: // 电子邮件
                                        employee.setEmail(cellValue);
                                        break;
                                    case 11: // 电话号码
                                        employee.setPhone(cellValue);
                                        break;
                                    case 12: // 联系地址
                                        employee.setAddress(cellValue);
                                        break;
                                    case 13: // 最高学历
                                        employee.setTiptopDegree(cellValue);
                                        break;
                                    case 14: // 毕业院校
                                        employee.setSchool(cellValue);
                                        break;
                                    case 15: // 专业名称
                                        employee.setSpecialty(cellValue);
                                        break;
                                    case 16: // 在职状态
                                        employee.setWorkState(cellValue);
                                        break;
                                    case 17: // 所属部门
                                        int departmentIndex = allDepartments.indexOf(new Department(cellValue));
                                        employee.setDepartmentId(allDepartments.get(departmentIndex).getId());
                                        break;
                                    case 18: // 职位
                                        int positionIndex = allPositions.indexOf(new Position(cellValue));
                                        employee.setPosId(allPositions.get(positionIndex).getId());
                                        break;
                                    case 19: // 职称
                                        int jobLevelIndex = allJobLevels.indexOf(new JobLevel(cellValue));
                                        employee.setJobLevelId(allJobLevels.get(jobLevelIndex).getId());
                                        break;
                                    case 20: // 聘用形式
                                        employee.setEngageForm(cellValue);
                                        break;
                                }
                                break;
                            default:
                                switch (k) {
                                    case 4: // 出生日期
                                        employee.setBirthday(cell.getDateCellValue());
                                        break;
                                    case 21: // 入职日期
                                        employee.setBeginDate(cell.getDateCellValue());
                                        break;
                                    case 22: // 转正日期
                                        employee.setConversionTime(cell.getDateCellValue());
                                        break;
                                    case 23: // 合同起始日期
                                        employee.setBeginContract(cell.getDateCellValue());
                                        break;
                                    case 24: // 合同截止日期
                                        employee.setEndContract(cell.getDateCellValue());
                                        break;
                                    case 25: //合同期限
                                        employee.setContractTerm(cell.getNumericCellValue());
                                        break;
                                }
                                break;
                        }
                    }
                    employeeList.add(employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employeeList;
    }
}
