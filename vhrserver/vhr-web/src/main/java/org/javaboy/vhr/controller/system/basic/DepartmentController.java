package org.javaboy.vhr.controller.system.basic;

import org.javaboy.vhr.model.Department;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/depart")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PostMapping("/")
    public RespBean addDep(@RequestBody Department dep) {
        departmentService.addDep(dep);
        if (dep.getResult() == 1) {
            return RespBean.ok("添加成功！", dep);
        }
        return RespBean.error("添加失败！");
    }

    @DeleteMapping("/")
    public RespBean deleteDep(@RequestBody Department dep) {
        departmentService.deleteDep(dep);
        if (dep.getResult() == -2) {
            return RespBean.error("该部门下有子部门，删除失败！");
        }
        if (dep.getResult() == -1) {
            return RespBean.error("该部门下有员工，删除失败！");
        }
        if (dep.getResult() == 1) {
            return RespBean.ok("删除成功！", dep);
        }
        return RespBean.error("删除失败！");
    }
}
