package app.BinarFudBackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setTo(email);
        messageHelper.setSubject("Verify OTP");
        messageHelper.setText("Hello, your OTP is " + otp);
//        messageHelper.setText(String.format("""
//           <div>
//              <a href="http://localhost:8080/verify-account?email=%s&otp=%s" target="_blank">
//                Klik Link untuk Verifikasi
//              </a>
//           </div>
//        """, email, otp), true);

        javaMailSender.send(mimeMessage);
    }

}
