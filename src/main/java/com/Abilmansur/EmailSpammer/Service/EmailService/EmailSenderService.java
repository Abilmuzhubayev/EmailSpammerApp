package com.Abilmansur.EmailSpammer.Service.EmailService;

import com.Abilmansur.EmailSpammer.Converters.MessageConverter;
import com.Abilmansur.EmailSpammer.DTO.MessageDTO;
import com.Abilmansur.EmailSpammer.Entity.MessageEntity;
import com.Abilmansur.EmailSpammer.Service.AppService.AppServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private AppServiceImplementation service;

    @Transactional
    public String sendEmail(MessageDTO messageDTO) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("networksenergyemailsenderapp@gmail.com");

        List<String> toEmail = service.getGroup(messageDTO.getGroupId()).getUserEmails();
        Object[] src = toEmail.toArray();
        String[] dest = new String[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        mailMessage.setBcc(dest);
        mailMessage.setText(messageDTO.getBody());
        mailMessage.setSubject(messageDTO.getSubject());
        try {
            javaMailSender.send(mailMessage);
        } catch (org.springframework.mail.MailSendException e) {
            if (e.getCause() != null && e.getCause().getClass().equals(com.sun.mail.util.MailConnectException.class)) {
                return "No connection";
            } else {
                return "No recipients";
            }
        }
        return "All good";
    }
}
