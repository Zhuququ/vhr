package org.javaboy.vhr.service;

import org.apache.ibatis.annotations.Param;
import org.javaboy.vhr.mapper.DepartmentMapper;
import org.javaboy.vhr.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    public void addDep(Department dep) {
        dep.setEnabled(true);
        // 使用存储过程
        // 思路： ①插入新纪录；②获取新纪录的id；③将parent的depPath + "." + id；④将parent的isParent设置为true
        departmentMapper.addDep(dep);
    }

    public void deleteDep(Department dep) {
        // 思路： ①若有子部门，不可删除；②若部门下有员工，不可删除；③上述两种情况外，删除；④若删除后，父部门无子部门，设置父部门isParent为false
        departmentMapper.deleteDep(dep);
    }

    public List<Department> getAllDepartmentsWithoutChildren() {
        return departmentMapper.getAllDepartmentsWithoutChildren();
    }
}
