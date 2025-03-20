package org.javaboy.vhr.task;

import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.MailConstants;
import org.javaboy.vhr.model.MailSendLog;
import org.javaboy.vhr.service.EmployeeService;
import org.javaboy.vhr.service.MailSendLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 员工离职处理任务
 */
@Component
public class EmployeeRemoveTask {
    @Autowired
    MailSendLogService mailSendLogService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    EmployeeService employeeService;

    /**
     * 每天的十点钟执行
     * 将员工离职后，发送邮件通知，并更改员工离职状态
     */
    @Scheduled(cron = "0 0 10 * * ?")
    public void mailResendTask() {
//        List<MailSendLog> logs = mailSendLogService.getMailSendLogsByStatus();
//        if (logs == null || logs.size() == 0) {
//            return;
//        }
//        logs.forEach(mailSendLog->{
//            if (mailSendLog.getCount() >= 3) {
//                mailSendLogService.updateMailSendLogStatus(mailSendLog.getMsgId(), 2);//直接设置该条消息发送失败
//            }else{
//                mailSendLogService.updateCount(mailSendLog.getMsgId(), new Date());
//                Employee emp = employeeService.getEmployeeById(mailSendLog.getEmpId());
//                rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME, emp, new CorrelationData(mailSendLog.getMsgId()));
//            }
//        });
    }

}
