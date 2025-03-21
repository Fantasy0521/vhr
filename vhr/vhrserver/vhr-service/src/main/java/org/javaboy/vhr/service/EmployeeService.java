package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeeMapper;
import org.javaboy.vhr.model.*;
import org.javaboy.vhr.model.vo.PaySalaryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @时间 2019-10-29 7:44
 */
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    MailSendLogService mailSendLogService;
    public final static Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    public RespPageBean getEmployeeByPage(Integer page, Integer size, Employee employee, Date[] beginDateScope) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> data = employeeMapper.getEmployeeByPage(page, size, employee, beginDateScope);
        calculateFinalSalary(data);
        Long total = employeeMapper.getTotal(employee, beginDateScope);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    /**
     * 计算最终工资
     * todo 浮点数计算的精度问题，尤其是在涉及货币计算时，后续需要使用`BigDecimal`来确保精度，但根据现有代码，使用的是`Float`，因此暂时按照`Float`处理。
     * 根据国家标准工资计算公式：
     * 应发工资 = 基本工资 + 交通补助 + 午餐补助 + 奖金
     * 扣除总额 = 养老金（基数×比率） + 医疗保险（基数×比率） + 公积金（基数×比率）
     * 最终工资 = 应发工资 - 扣除总额
     * @param data
     */
    private void calculateFinalSalary(List<Employee> data) {
        for (Employee employee : data) {
            Salary salary = employee.getSalary();
            if (salary == null) {
                return;
            }
            PaySalaryVo paySalaryVo = new PaySalaryVo();

            // 计算应发工资
            float totalIncome = (salary.getAllSalary() != null ? salary.getAllSalary() : 0)
                    + (salary.getTrafficSalary() != null ? salary.getTrafficSalary() : 0)
                    + (salary.getLunchSalary() != null ? salary.getLunchSalary() : 0)
                    + (salary.getBonus() != null ? salary.getBonus() : 0);

            // 计算社保公积金扣除
            float pension = (salary.getPensionBase() != null ? salary.getPensionBase() : 0)
                    * (salary.getPensionPer() != null ? salary.getPensionPer() : 0);
            float medical = (salary.getMedicalBase() != null ? salary.getMedicalBase() : 0)
                    * (salary.getMedicalPer() != null ? salary.getMedicalPer() : 0);
            float fund = (salary.getAccumulationFundBase() != null ? salary.getAccumulationFundBase() : 0)
                    * (salary.getAccumulationFundPer() != null ? salary.getAccumulationFundPer() : 0);

            // 最终工资 = 应发工资 - 总扣除
            Float finalSalary = totalIncome - (pension + medical + fund);

            paySalaryVo.setFinalSalary(finalSalary);
            paySalaryVo.setShouldPaySalary(totalIncome);
            //养老金、医疗保险、公积金
            paySalaryVo.setPension(pension);
            paySalaryVo.setMedical(medical);
            paySalaryVo.setFund(fund);

            employee.setPaySalaryVo(paySalaryVo);
        }
    }

    public Integer addEmp(Employee employee) {
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        double month = (Double.parseDouble(yearFormat.format(endContract)) - Double.parseDouble(yearFormat.format(beginContract))) * 12 + (Double.parseDouble(monthFormat.format(endContract)) - Double.parseDouble(monthFormat.format(beginContract)));
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(month / 12)));
        int result = employeeMapper.insertSelective(employee);
        if (result == 1) {
            Employee emp = employeeMapper.getEmployeeById(employee.getId());
            //生成消息的唯一id
            String msgId = UUID.randomUUID().toString();
            MailSendLog mailSendLog = new MailSendLog();
            mailSendLog.setMsgId(msgId);
            mailSendLog.setCreateTime(new Date());
            mailSendLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailSendLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailSendLog.setEmpId(emp.getId());
            mailSendLog.setTryTime(new Date(System.currentTimeMillis() + 1000 * 60 * MailConstants.MSG_TIMEOUT));
            mailSendLogService.insert(mailSendLog);
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME, emp, new CorrelationData(msgId));
        }
        return result;
    }

    public Integer maxWorkID() {
        return employeeMapper.maxWorkID();
    }

    public Integer deleteEmpByEid(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public Integer updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public Integer addEmps(List<Employee> list) {
        return employeeMapper.addEmps(list);
    }

    public RespPageBean getEmployeeByPageWithSalary(Integer page, Integer size) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> list = employeeMapper.getEmployeeByPageWithSalary(page, size);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(list);
        respPageBean.setTotal(employeeMapper.getTotal(null, null));
        return respPageBean;
    }

    public Integer updateEmployeeSalaryById(Integer eid, Integer sid) {
        return employeeMapper.updateEmployeeSalaryById(eid, sid);
    }

    public Employee getEmployeeById(Integer empId) {
        return employeeMapper.getEmployeeById(empId);
    }
}
