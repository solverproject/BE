package com.solver.solver_be.global.util.email;

import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/email/{email_addr}")
    public ResponseEntity<GlobalResponseDto> sendEmailPath(@PathVariable String email_addr) throws MessagingException {
        emailService.sendEmail(email_addr);
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.EMAIL_CHECK));
    }


}
