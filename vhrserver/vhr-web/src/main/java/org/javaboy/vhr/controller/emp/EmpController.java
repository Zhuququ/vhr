package org.javaboy.vhr.controller.emp;

import org.javaboy.vhr.model.*;
import org.javaboy.vhr.service.*;
import org.javaboy.vhr.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/employee/basic")
public class EmpController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PoliticsstatusService politicsstatusService;

    @Autowired
    NationService nationService;

    @Autowired
    JobLevelService jobLevelService;

    @Autowired
    PositionService positionService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    // @RequestParam：url形式为（?key=value&key2=value2）key值与参数名一致时，可以不设置该注解的value属性
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, Employee employee, Date[] beginDateScope) {
        return employeeService.getEmployeeByPage(page, size, employee, beginDateScope);
    }

    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee) {
        if (employeeService.addEmp(employee) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee) {
        if (employeeService.updateEmp(employee) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmpById(@PathVariable Integer id) {
        if (employeeService.deleteEmpById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @GetMapping("/politics")
    public List<Politicsstatus> getAllPolitics() {
        return politicsstatusService.getAllPolitics();
    }

    @GetMapping("/nations")
    public List<Nation> getAllNations() {
        return nationService.getAllNations();
    }

    @GetMapping("/joblevels")
    public List<JobLevel> getAllJobLevels() {
        return jobLevelService.getAllJobLevels();
    }

    @GetMapping("/positions")
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    @GetMapping("/maxworkid")
    public RespBean getMaxWorkID() {
        RespBean respBean = RespBean.build().setStatus(200).setObj(String.format("%08d", employeeService.getMaxWorkID() + 1));
        return respBean;
    }

    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/export")
    public ResponseEntity exportEmp2Excel() {
        return POIUtils.emp2Excel(((List<Employee>) employeeService.getEmployeeByPage(null, null, null, null).getData()));
    }

    @PostMapping("/import")
    public RespBean importExcel4Emp(MultipartFile file) throws IOException {
        List<Employee> employeeList = POIUtils.excel2Emp(file, nationService.getAllNations(),politicsstatusService.getAllPolitics(), departmentService.getAllDepartmentsWithoutChildren(), positionService.getAllPositions(), jobLevelService.getAllJobLevels());

        if (employeeService.addEmps(employeeList) == employeeList.size()) {
            return RespBean.ok("上传成功！");
        }
        return RespBean.error("上传失败！");
    }
}
