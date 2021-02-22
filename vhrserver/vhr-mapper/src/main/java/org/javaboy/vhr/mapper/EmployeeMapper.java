package org.javaboy.vhr.mapper;

import org.apache.ibatis.annotations.Param;
import org.javaboy.vhr.model.Employee;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    List<Employee> getEmployeeByPage(@Param("page") Integer page, @Param("size") Integer size,@Param("emp") Employee employee,@Param("beginDateScope") Date[] beginDateScope);

    Long getEmployeeTotal(@Param("emp") Employee employee,@Param("beginDateScope") Date[] beginDateScope);

    Integer getMaxWorkID();

    Integer addEmps(@Param("list") List<Employee> employeeList);

    Employee getEmployeeById(Integer id);

    List<Employee> getEmployeeWithSalaryByPage(@Param("page") Integer page,@Param("size") Integer size);

    Integer updateEmployeeWithSalary(Integer eid, Integer sid);
}