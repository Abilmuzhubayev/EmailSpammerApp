package com.Abilmansur.EmailSpammer.Aspects;

import com.Abilmansur.EmailSpammer.DTO.MessageDTO;
import com.Abilmansur.EmailSpammer.Entity.MessageEntity;
import com.Abilmansur.EmailSpammer.Service.AppService.AppServiceImplementation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Aspect
public class EmailRecorderAspect {

    @Autowired
    AppServiceImplementation appService;

    @Transactional
    @AfterReturning(value = "execution(public String sendMessage(..))", returning = "returnValue")
    public void recordEmail(JoinPoint joinPoint, Object returnValue) {
        if ((String) returnValue != "redirect:/emailRecords") return;
        Object[] args = joinPoint.getArgs();
        MessageDTO messageDTO = (MessageDTO) args[0];
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        messageDTO.setLocalTime(dtf.format(now));
        appService.saveMessage(messageDTO);
    }
}
