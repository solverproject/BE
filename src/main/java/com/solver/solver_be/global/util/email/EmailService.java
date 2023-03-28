package com.solver.solver_be.global.util.email;

import com.solver.solver_be.domain.company.dto.CompanyRequestDto;
import com.solver.solver_be.domain.company.service.CompanyService;
import com.solver.solver_be.global.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;
    private final CompanyService companyService;

    @Value("${spring.mail.username}")
    private String configEmail;

    private MimeMessage createEmailForm(String email) throws MessagingException {

        CompanyRequestDto companyRequestDto = new CompanyRequestDto();

        String auth = companyService.createCompanyToken(companyRequestDto);

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("메세지가 도착 했습니다.");
        message.setFrom(configEmail);
        message.setText(auth, "utf-8", "html");

        redisUtil.setDataExpire(email, auth, 60 * 30L);

        return message;
    }

    // 메일 보내기
    public void sendEmail(String toEmail) throws MessagingException {
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }

        MimeMessage emailForm = createEmailForm(toEmail);

        mailSender.send(emailForm);
    }
}