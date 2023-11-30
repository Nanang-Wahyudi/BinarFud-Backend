package app.BinarFudBackend.controller;

import app.BinarFudBackend.model.request.EmailRequest;
import app.BinarFudBackend.model.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
public class EmailController {

    @Autowired
    private JavaMailSender javaMailSender;

//    Hanya Testing Email Semata
    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setTo(request.getTo());
        messageHelper.setSubject(request.getSubject());
        messageHelper.setText(request.getMessage());
        javaMailSender.send(mimeMessage);

        return ResponseEntity.ok()
                .body(Response.builder()
                        .data(request)
                        .successMessage("Email Sent Successful.")
                        .isSuccess(Boolean.TRUE)
                        .build());
    }

}
