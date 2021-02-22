package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeeMapper;
import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.RespPageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeService {
    public static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.##");

    public RespPageBean getEmployeeByPage(Integer page, Integer size, Employee employee, Date[] beginDateScope) {
        if (page != null && size != null) {
            page = (page - 1) * size;// Mysql中limit的参数1：偏移量（从0开始）,这边的page其实代表的是第n条开始
        }
        List<Employee> data = employeeMapper.getEmployeeByPage(page, size, employee, beginDateScope);
        Long total = employeeMapper.getEmployeeTotal(employee, beginDateScope);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(data);
        respPageBean.setTotal(total);
        return respPageBean;
    }

    public Integer addEmp(Employee employee) {
        employee.setContractTerm(this.caculateContractTerm(employee));
        int result = employeeMapper.insertSelective(employee);
        if (result == 1) {
            Employee employee1 = employeeMapper.getEmployeeById(employee.getId());
            LOGGER.info(employee1.toString());
            rabbitTemplate.convertAndSend("javaboy.mail.welcome", employee1);
        }
        return result;
    }

    public Integer getMaxWorkID() {
        return employeeMapper.getMaxWorkID();
    }

    public Integer deleteEmpById(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public Integer updateEmp(Employee employee) {
        employee.setContractTerm(this.caculateContractTerm(employee));
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    protected Double caculateContractTerm(Employee employee) {
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        double month = ((Double.parseDouble(yearFormat.format(endContract)) - Double.parseDouble(yearFormat.format(beginContract))) * 12
                + (Double.parseDouble(monthFormat.format(endContract)) - Double.parseDouble(monthFormat.format(beginContract))));
        return Double.parseDouble(decimalFormat.format(month / 12));
    }

    public Integer addEmps(List<Employee> employeeList) {
        return employeeMapper.addEmps(employeeList);
    }

    public RespPageBean getEmployeeWithSalaryByPage(Integer page, Integer size) {
        RespPageBean respPageBean = new RespPageBean();
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> employeeList = employeeMapper.getEmployeeWithSalaryByPage(page, size);
        Long total = employeeMapper.getEmployeeTotal(null, null);
        respPageBean.setData(employeeList);
        respPageBean.setTotal(total);
        return respPageBean;
    }

    public Integer updateEmployeeWithSalary(Integer eid, Integer sid) {
        return employeeMapper.updateEmployeeWithSalary(eid, sid);
    }
}
